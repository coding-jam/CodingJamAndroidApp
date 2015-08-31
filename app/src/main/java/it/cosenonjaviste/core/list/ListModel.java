package it.cosenonjaviste.core.list;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.Bagger;

import java.util.List;

import it.cosenonjaviste.core.utils.ObservableArrayListBagger;

public abstract class ListModel<T> implements Parcelable {

    public ObservableBoolean error = new ObservableBoolean();

    public boolean loaded;

    @Bagger(ObservableArrayListBagger.class)
    public ObservableArrayList<T> items = new ObservableArrayList<>();

    public final ObservableArrayList<T> getItems() {
        return items;
    }

    public boolean isLoaded() {
        return loaded || error.get();
    }

    public void clear() {
        getItems().clear();
    }

    public void done(List<T> items) {
        loaded = true;
        getItems().clear();
        getItems().addAll(items);
        error.set(false);
    }

    public void append(List<T> items) {
        loaded = true;
        getItems().addAll(items);
        error.set(false);
    }

    public void error() {
        clear();
        error.set(true);
    }
}
