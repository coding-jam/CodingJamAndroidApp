package it.cosenonjaviste.core.model;

import java.util.List;

public class CategoryResponse {
    private List<Category> categories;

    public CategoryResponse() {
    }

    public CategoryResponse(List<Category> categories) {
        this();
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
