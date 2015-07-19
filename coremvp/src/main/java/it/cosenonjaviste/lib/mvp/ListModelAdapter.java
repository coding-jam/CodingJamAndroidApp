package it.cosenonjaviste.lib.mvp;

import java.util.List;

import it.cosenonjaviste.bind.BindableBoolean;

public abstract class ListModelAdapter<T> implements ListModel {
    public BindableBoolean empty = new BindableBoolean(true);

    public BindableBoolean error = new BindableBoolean();

    protected abstract List<T> getItems();

    protected abstract void setItems(List<T> items);

    public boolean isLoaded() {
        return getItems() != null || error.get();
    }
}
