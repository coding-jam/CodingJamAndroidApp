package it.cosenonjaviste.category;

import java.util.List;

import it.cosenonjaviste.model.Category;

public class CategoryListModel {
    List<Category> items;

    boolean errorLoading;

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public Category get(int index) {
        return items.get(index);
    }

    public void done(List<Category> items) {
        this.items = items;
        errorLoading = false;
    }

    public void error() {
        items = null;
        errorLoading = true;
    }

    public List<Category> getItems() {
        return items;
    }

    public boolean isError() {
        return errorLoading;
    }
}
