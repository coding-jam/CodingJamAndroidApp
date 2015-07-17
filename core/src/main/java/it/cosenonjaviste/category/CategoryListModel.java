package it.cosenonjaviste.category;

import org.parceler.Transient;

import java.util.List;

import it.cosenonjaviste.ListModel;
import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.model.Category;

public class CategoryListModel implements ListModel {
    List<Category> items;

    BindableBoolean empty = new BindableBoolean(true);

    BindableBoolean error = new BindableBoolean();

    @Transient
    BindableBoolean loading = new BindableBoolean();

    @Transient
    BindableBoolean loadingMore = new BindableBoolean();

    public int size() {
        return items.size();
    }

    public Category get(int index) {
        return items.get(index);
    }

    public void done(List<Category> items) {
        this.items = items;
        error.set(false);
        empty.set(items.isEmpty());
    }

    public void error() {
        items = null;
        error.set(true);
        empty.set(false);
    }

    public List<Category> getItems() {
        return items;
    }

    public BindableBoolean isEmptyLayoutVisible() {
        return empty.and(loading.not());
    }

    public BindableBoolean isListVisible() {
        return empty.not().and(loading.not());
    }

    public BindableBoolean isError() {
        return error;
    }

    public BindableBoolean isLoading() {
        return loading;
    }

    public BindableBoolean isLoadingMore() {
        return loadingMore;
    }
}
