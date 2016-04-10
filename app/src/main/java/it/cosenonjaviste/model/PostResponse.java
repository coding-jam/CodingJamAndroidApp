package it.cosenonjaviste.model;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;

import java.util.List;

@AutoValue
public abstract class PostResponse {

    public static PostResponse create(List<Post> posts) {
        return new AutoValue_PostResponse(posts);
    }

    public abstract List<Post> posts();

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_PostResponse.typeAdapterFactory();
    }
}
