package it.cosenonjaviste.ui.category;

import it.cosenonjaviste.BR;
import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class CategoryViewHolder extends BindableViewHolder<CategoryRowBinding, Category> {

    public CategoryViewHolder(CategoryRowBinding binding, CategoryListViewModel viewModel) {
        super(binding, BR.category);
        binding.getRoot().setOnClickListener(v -> viewModel.goToPosts(position));
    }
}
