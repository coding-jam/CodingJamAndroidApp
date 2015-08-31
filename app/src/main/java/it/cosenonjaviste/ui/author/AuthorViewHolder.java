package it.cosenonjaviste.ui.author;

import it.cosenonjaviste.BR;
import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class AuthorViewHolder extends BindableViewHolder<AuthorCellBinding, Author> {

    public AuthorViewHolder(AuthorCellBinding binding, AuthorListViewModel viewModel) {
        super(binding, BR.author);
        binding.getRoot().setOnClickListener(v -> viewModel.goToAuthorDetail(position));
    }
}
