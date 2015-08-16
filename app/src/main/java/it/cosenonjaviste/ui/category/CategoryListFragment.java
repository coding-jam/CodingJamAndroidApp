package it.cosenonjaviste.ui.category;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.category.CategoryListPresenter;
import it.cosenonjaviste.core.category.CategoryListView;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.BindableViewHolder;
import it.cosenonjaviste.ui.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class CategoryListFragment extends RecyclerViewRxMvpFragment<CategoryListPresenter, Category> implements CategoryListView {

    @Override protected CategoryListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getCategoryListPresenter();
    }

    @NonNull @Override protected BindableViewHolder<Category> createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new CategoryViewHolder(CategoryRowBinding.bind(inflater.inflate(R.layout.category_row, v, false)), presenter);
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
