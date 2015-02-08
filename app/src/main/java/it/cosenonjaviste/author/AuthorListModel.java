package it.cosenonjaviste.author;

import org.parceler.Parcel;

import java.util.List;

import it.cosenonjaviste.lib.mvp.utils.OptionalItem;
import it.cosenonjaviste.lib.mvp.utils.OptionalList;
import it.cosenonjaviste.model.Author;
import rx.functions.Action1;

@Parcel
public class AuthorListModel {
    OptionalList<Author> items = new OptionalList<>();

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public Author get(int index) {
        return items.get(index);
    }

    public void done(List<Author> object) {
        items.done(object);
    }

    public void error(Throwable throwable) {
        items.error(throwable);
    }

    public OptionalItem<List<Author>> call(Action1<List<Author>> action) {
        return items.call(action);
    }
}
