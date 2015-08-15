package it.cosenonjaviste.lib.mvp;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import java.util.List;

public abstract class ListModelAdapter<T> {

    public ObservableBoolean error = new ObservableBoolean();

    public boolean loaded;

    public abstract ObservableArrayList<T> getItems();

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
