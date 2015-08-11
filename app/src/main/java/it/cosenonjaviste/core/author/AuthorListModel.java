package it.cosenonjaviste.core.author;

import java.util.List;

import it.cosenonjaviste.core.model.Author;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;

public class AuthorListModel extends ListModelAdapter<Author> {
    List<Author> items;

    public int size() {
        return items.size();
    }

    public Author get(int index) {
        return items.get(index);
    }

    public List<Author> getItems() {
        return items;
    }

    @Override public void setItems(List<Author> items) {
        this.items = items;
    }
}
