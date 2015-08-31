package it.cosenonjaviste.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.category.CategoryListView;
import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.lib.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class CategoryListFragment extends ViewModelFragment<CategoryListViewModel> implements CategoryListView {

    @Override protected CategoryListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getCategoryListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .gridLayoutManager(2)
                .viewHolder(CategoryRowBinding::inflate, CategoryRowBinding::setCategory, vh -> viewModel.goToPosts(vh.getAdapterPosition()))
                .getRoot();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
