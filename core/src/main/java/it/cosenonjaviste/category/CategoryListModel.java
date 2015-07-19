package it.cosenonjaviste.category;

import java.util.List;

import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Category;

public class CategoryListModel extends ListModelAdapter<Category> {
    List<Category> items;

    public Category get(int index) {
        return items.get(index);
    }

    @Override public List<Category> getItems() {
        return items;
    }

    @Override public void setItems(List<Category> items) {
        this.items = items;
    }
}
