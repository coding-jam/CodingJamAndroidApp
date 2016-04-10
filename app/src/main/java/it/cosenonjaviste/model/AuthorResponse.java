package it.cosenonjaviste.model;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;

import java.util.List;

@AutoValue
public abstract class AuthorResponse {

    public static AuthorResponse create(List<Author> authors) {
        return new AutoValue_AuthorResponse(authors);
    }

    public abstract List<Author> authors();

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_AuthorResponse.typeAdapterFactory();
    }
}
