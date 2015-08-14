package it.cosenonjaviste.core.twitter;

import java.util.List;

import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Tweet;

public class TweetListModel extends ListModelAdapter<Tweet> {
    List<Tweet> items;

    boolean moreDataAvailable;

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void append(List<Tweet> object) {
        items.addAll(object);
    }

    public List<Tweet> getItems() {
        return items;
    }

    @Override protected void setItems(List<Tweet> items) {
        this.items = items;
    }
}
