package it.cosenonjaviste.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(CategoryListModel.class)
public class CategoryListFragment extends RecyclerViewRxMvpFragment<CategoryListPresenter, Category> implements CategoryListView {

    @Override protected CategoryListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getCategoryListPresenter();
    }

    @NonNull @Override protected BindableViewHolder<Category> createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new CategoryViewHolder(CategoryRowBinding.bind(inflater.inflate(R.layout.category_row, v, false)), presenter);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.setPresenter(presenter);
        binding.swipeRefresh.setOnRefreshListener(presenter::loadDataPullToRefresh);
        presenter.setListChangeListener(adapter::reloadData);
        return view;
    }

    @Override protected void retry() {
        presenter.loadData();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
