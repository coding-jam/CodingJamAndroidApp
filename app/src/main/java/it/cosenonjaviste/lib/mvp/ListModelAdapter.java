package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableBoolean;

import java.util.List;

import it.cosenonjaviste.core.utils.ObservableParcelerArrayList;

public abstract class ListModelAdapter<T> {

    public ObservableBoolean error = new ObservableBoolean();

    public boolean loaded;

    public abstract ObservableParcelerArrayList<T> getItems();

    public void append(List<T> object) {
        loaded = true;
        getItems().addAll(object);
    }

    public boolean isLoaded() {
        return loaded || error.get();
    }

    public void clear() {
        getItems().clear();
    }
}
