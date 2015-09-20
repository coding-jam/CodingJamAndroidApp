package it.cosenonjaviste.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class CategoryListFragment extends ViewModelFragment<CategoryListViewModel> {

    @Override public CategoryListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getCategoryListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .gridLayoutManager(2)
                .viewHolder(CategoryRowBinding::inflate, CategoryRowBinding::setCategory, vh -> viewModel.goToPosts(vh.getAdapterPosition()))
                .getRoot();
    }
}
