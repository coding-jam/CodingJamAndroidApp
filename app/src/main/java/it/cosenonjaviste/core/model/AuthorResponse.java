package it.cosenonjaviste.core.model;

import java.util.List;

public class AuthorResponse {
    private List<Author> authors;

    public AuthorResponse() {
    }

    public AuthorResponse(List<Author> authors) {
        this();
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
