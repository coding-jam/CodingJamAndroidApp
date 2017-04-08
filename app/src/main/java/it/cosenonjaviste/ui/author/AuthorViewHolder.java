package it.cosenonjaviste.ui.author;

import android.databinding.ObservableField;

import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.ui.recycler.BindableViewHolder;


public class AuthorViewHolder extends BindableViewHolder<Author> {
    public final ObservableField<Author> item = new ObservableField<>();

    private AuthorCellBinding binding;

    private AuthorListViewModel viewModel;

    public AuthorViewHolder(AuthorCellBinding binding, AuthorListViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        binding.setViewHolder(this);
    }

    @Override public void bind(Author item) {
        this.item.set(item);
        binding.executePendingBindings();
    }

    public void onItemClicked() {
        viewModel.goToAuthorDetail(getAdapterPosition());
    }
}
