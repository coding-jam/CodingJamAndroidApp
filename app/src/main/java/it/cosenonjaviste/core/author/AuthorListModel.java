package it.cosenonjaviste.core.author;

import it.cosenonjaviste.core.utils.ObservableParcelerArrayList;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Author;

public class AuthorListModel extends ListModelAdapter<Author> {
    ObservableParcelerArrayList<Author> items = new ObservableParcelerArrayList<>();

    public int size() {
        return items.size();
    }

    public Author get(int index) {
        return items.get(index);
    }

    public ObservableParcelerArrayList<Author> getItems() {
        return items;
    }
}
