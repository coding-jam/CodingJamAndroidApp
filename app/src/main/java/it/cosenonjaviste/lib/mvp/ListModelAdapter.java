package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;

import java.util.List;

public abstract class ListModelAdapter<T> {
    public ObservableBoolean empty = new ObservableBoolean(true);

    public ObservableBoolean error = new ObservableBoolean();

    protected abstract List<T> getItems();

    protected abstract void setItems(List<T> items);

    public boolean isLoaded() {
        return getItems() != null || error.get();
    }
}
