package it.cosenonjaviste.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class AuthorResponse {

    public static AuthorResponse create(List<Author> authors) {
        return new AutoValue_AuthorResponse(authors);
    }

    public abstract List<Author> authors();

    public static TypeAdapter<AuthorResponse> typeAdapter(Gson gson) {
        return new AutoValue_AuthorResponse.GsonTypeAdapter(gson);
    }
}
