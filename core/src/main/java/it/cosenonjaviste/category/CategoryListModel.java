package it.cosenonjaviste.category;

import java.util.List;

import it.cosenonjaviste.ListModelAdapter;
import it.cosenonjaviste.model.Category;

public class CategoryListModel extends ListModelAdapter<Category> {
    List<Category> items;

    public int size() {
        return items.size();
    }

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
