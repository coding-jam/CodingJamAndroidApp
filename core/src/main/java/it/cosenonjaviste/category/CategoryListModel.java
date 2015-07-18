package it.cosenonjaviste.category;

import org.parceler.Transient;

import java.util.List;

import it.cosenonjaviste.ListModel;
import it.cosenonjaviste.model.Category;
import rx.functions.Action1;

public class CategoryListModel extends ListModel {
    List<Category> items;

    public int size() {
        return items.size();
    }

    public Category get(int index) {
        return items.get(index);
    }

    @Transient
    Action1<List<Category>> listChangeListener;

    public void done(List<Category> items) {
        this.items = items;
        error.set(false);
        empty.set(items.isEmpty());
        if (listChangeListener != null) {
            listChangeListener.call(items);
        }
    }

    public void error() {
        this.items = null;
        error.set(true);
        empty.set(false);
    }

    public void setListChangeListener(Action1<List<Category>> listChangeListener) {
        this.listChangeListener = listChangeListener;
        if (listChangeListener != null && items != null) {
            listChangeListener.call(items);
        }
    }

    public List<Category> getItems() {
        return items;
    }
}
