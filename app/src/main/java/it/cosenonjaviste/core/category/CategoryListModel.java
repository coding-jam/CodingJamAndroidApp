package it.cosenonjaviste.core.category;

import java.util.List;

import it.cosenonjaviste.core.model.Category;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;

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
