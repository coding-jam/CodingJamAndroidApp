package it.cosenonjaviste.ui.category;

import android.databinding.ObservableField;

import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.ui.recycler.BindableViewHolder;


public class CategoryViewHolder extends BindableViewHolder<Category> {
    public final ObservableField<Category> item = new ObservableField<>();

    private CategoryRowBinding binding;

    private CategoryListViewModel viewModel;

    public CategoryViewHolder(CategoryRowBinding binding, CategoryListViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        binding.setViewHolder(this);
    }

    @Override public void bind(Category item) {
        this.item.set(item);
        binding.executePendingBindings();
    }

    public void onItemClicked() {
        viewModel.goToPosts(getAdapterPosition());
    }
}
