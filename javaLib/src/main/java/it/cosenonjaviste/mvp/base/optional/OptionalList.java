package it.cosenonjaviste.mvp.base.optional;

import java.util.ArrayList;
import java.util.List;

public class OptionalList<T> extends OptionalItem<List<T>> {
    public OptionalList() {
        this(new ArrayList<>());
    }

    public OptionalList(List<T> object) {
        super(object);
    }

    public OptionalList(List<T> object, Throwable throwable) {
        super(object, throwable);
    }

    @Override public boolean isEmpty() {
        return super.isEmpty() || getObject().isEmpty();
    }

    public int size() {
        if (isValueAvalaible()) {
            return getObject().size();
        } else {
            return 0;
        }
    }

    public T get(int index) {
        if (isValueAvalaible()) {
            return getObject().get(index);
        } else {
            return null;
        }
    }

    public void append(List<T> object) {
        throwable = null;
        this.object.addAll(object);
    }
}
