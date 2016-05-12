package it.cosenonjaviste.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class PostResponse {

    public static PostResponse create(List<Post> posts) {
        return new AutoValue_PostResponse(posts);
    }

    public abstract List<Post> posts();

    public static TypeAdapter<PostResponse> typeAdapter(Gson gson) {
        return new AutoValue_PostResponse.GsonTypeAdapter(gson);
    }
}
