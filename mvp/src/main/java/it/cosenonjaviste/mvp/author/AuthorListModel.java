package it.cosenonjaviste.mvp.author;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class AuthorListModel {
    OptionalList authors = new OptionalList<>();

    public OptionalList<Author> getAuthors() {
        return authors;
    }
}
