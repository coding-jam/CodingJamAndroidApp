package it.cosenonjaviste.core.twitter;

import it.cosenonjaviste.core.utils.ObservableParcelerArrayList;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Tweet;

public class TweetListModel extends ListModelAdapter<Tweet> {
    ObservableParcelerArrayList<Tweet> items = new ObservableParcelerArrayList<>();

    boolean moreDataAvailable;

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public ObservableParcelerArrayList<Tweet> getItems() {
        return items;
    }
}
