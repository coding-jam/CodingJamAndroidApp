package it.cosenonjaviste.twitter;

import java.util.List;

import it.cosenonjaviste.model.Tweet;

public class TweetListModel {
    List<Tweet> list;

    boolean errorLoading;

    boolean moreDataAvailable;

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    public void done(List<Tweet> list) {
        this.list = list;
        errorLoading = false;
    }

    public void error() {
        list = null;
        errorLoading = true;
    }

    public int size() {
        return list.size();
    }

    public boolean isError() {
        return errorLoading;
    }

    public void append(List<Tweet> object) {
        list.addAll(object);
    }

    public List<Tweet> getItems() {
        return list;
    }
}
