package it.cosenonjaviste.ui.category;

import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class CategoryViewHolder extends BindableViewHolder<CategoryRowBinding, Category> {

    private int position;

    public CategoryViewHolder(CategoryRowBinding binding, CategoryListViewModel viewModel) {
        super(binding);
        binding.getRoot().setOnClickListener(v -> viewModel.goToPosts(position));
    }

    @Override public void bind(Category category, int position) {
        this.position = position;
        binding.setCategory(category);
    }
}
