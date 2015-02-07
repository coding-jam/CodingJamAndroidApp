package it.cosenonjaviste.category;

import org.parceler.Parcel;

import java.util.List;

import it.cosenonjaviste.lib.mvp.OptionalItem;
import it.cosenonjaviste.lib.mvp.OptionalList;
import it.cosenonjaviste.model.Category;
import rx.functions.Action1;

@Parcel
public class CategoryListModel {
    OptionalList<Category> items = new OptionalList<>();

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public Category get(int index) {
        return items.get(index);
    }

    public void done(List<Category> object) {
        items.done(object);
    }

    public void error(Throwable throwable) {
        items.error(throwable);
    }

    public OptionalItem<List<Category>> call(Action1<List<Category>> action) {
        return items.call(action);
    }
}
