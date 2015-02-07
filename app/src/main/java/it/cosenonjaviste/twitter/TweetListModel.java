package it.cosenonjaviste.twitter;

import org.parceler.Parcel;

import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

@Parcel
public class TweetListModel extends OptionalList<Tweet> {
    private boolean moreDataAvailable;

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }
}
