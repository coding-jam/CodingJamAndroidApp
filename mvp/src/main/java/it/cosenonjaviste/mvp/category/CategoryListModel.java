package it.cosenonjaviste.mvp.category;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class CategoryListModel {
    OptionalList<Category> categories = new OptionalList<>();

    public OptionalList<Category> getCategories() {
        return categories;
    }
}
