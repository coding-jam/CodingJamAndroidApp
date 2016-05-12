package it.cosenonjaviste.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class CategoryResponse {

    public static CategoryResponse create(List<Category> categories) {
        return new AutoValue_CategoryResponse(categories);
    }

    public abstract List<Category> categories();

    public static TypeAdapter<CategoryResponse> typeAdapter(Gson gson) {
        return new AutoValue_CategoryResponse.GsonTypeAdapter(gson);
    }
}
