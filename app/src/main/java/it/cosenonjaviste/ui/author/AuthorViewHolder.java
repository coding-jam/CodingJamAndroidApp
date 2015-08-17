package it.cosenonjaviste.ui.author;

import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class AuthorViewHolder extends BindableViewHolder<Author> {

    private final AuthorCellBinding binding;

    private int position;

    public AuthorViewHolder(AuthorCellBinding binding, AuthorListPresenter presenter) {
        super(binding.getRoot());
        this.binding = binding;
        binding.getRoot().setOnClickListener(v -> presenter.goToAuthorDetail(position));
    }

    @Override public void bind(Author author, int position) {
        this.position = position;
        binding.setAuthor(author);
    }
}
