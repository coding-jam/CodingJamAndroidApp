package it.cosenonjaviste.core.category;

import it.cosenonjaviste.core.utils.ObservableParcelerArrayList;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Category;

public class CategoryListModel extends ListModelAdapter<Category> {
    ObservableParcelerArrayList<Category> items = new ObservableParcelerArrayList<>();

    public Category get(int index) {
        return items.get(index);
    }

    @Override public ObservableParcelerArrayList<Category> getItems() {
        return items;
    }
}
