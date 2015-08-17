package it.cosenonjaviste.ui.category;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.category.CategoryListViewModel;
import it.cosenonjaviste.databinding.CategoryRowBinding;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class CategoryViewHolder extends BindableViewHolder<Category> {

    private CategoryRowBinding binding;

    private int position;

    public CategoryViewHolder(CategoryRowBinding binding, CategoryListViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        binding.getRoot().setOnClickListener(v -> viewModel.goToPosts(position));
    }

    @Override public void bind(Category category, int position) {
        this.position = position;
        binding.setCategory(category);
        //TODO convert to data binding
        binding.categoryPosts.setText(itemView.getContext().getString(R.string.post_count, category.getPostCount()));
    }
}
