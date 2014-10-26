package it.cosenonjaviste.mvp.author;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.base.optional.OptionalItem;

public class AuthorListModel {
    OptionalItem authors = new OptionalItem<>(new ArrayList<>());

    public OptionalItem<List<Author>> getAuthorsModel() {
        return authors;
    }

    public List<Author> getAuthors() {
        return getAuthorsModel().getObject();
    }
}
