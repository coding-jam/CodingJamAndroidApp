package it.cosenonjaviste.mvp.author;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.LoadableModel;

public class AuthorListModel {
    LoadableModel authors = new LoadableModel<>(new ArrayList<>());

    public LoadableModel<List<Author>> getAuthorsModel() {
        return authors;
    }

    public List<Author> getAuthors() {
        return getAuthorsModel().getObject();
    }
}
