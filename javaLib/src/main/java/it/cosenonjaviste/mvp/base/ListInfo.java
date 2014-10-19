package it.cosenonjaviste.mvp.base;

import java.util.List;

public class ListInfo implements LoadingListener<Integer, List<?>> {
    boolean loadingItems;
    boolean loadingMoreItems;
    Throwable listLoadingError;
    int lastLoadedPage;
    Throwable listLoadingMoreError;
    boolean moreItemsAvailable;

    public boolean isLoadingItems() {
        return loadingItems;
    }

    public boolean isLoadingMoreItems() {
        return loadingMoreItems;
    }

    public Throwable getListLoadingError() {
        return listLoadingError;
    }

    public void start(Integer page) {
        loadingItems = isReloadAllCall(page);
        loadingMoreItems = !loadingItems;
        listLoadingError = null;
        listLoadingMoreError = null;
    }

    private boolean isReloadAllCall(Integer page) {
        return page == null || page == 0;
    }

    public void error(Integer param, Throwable t1) {
        if (isReloadAllCall(param)) {
            listLoadingError = t1;
        } else {
            listLoadingMoreError = t1;
        }
        loadingItems = false;
        loadingMoreItems = false;
    }

    public void done(Integer param, List<?> result) {
        loadingItems = false;
        loadingMoreItems = false;
        moreItemsAvailable = !result.isEmpty();
    }

    public int getLastLoadedPage() {
        return lastLoadedPage;
    }

    public void setLastLoadedPage(int lastLoadedPage) {
        this.lastLoadedPage = lastLoadedPage;
    }

    public Throwable getListLoadingMoreError() {
        return listLoadingMoreError;
    }

    public void setListLoadingMoreError(Throwable listLoadingMoreError) {
        this.listLoadingMoreError = listLoadingMoreError;
    }

    public boolean isMoreItemsAvailable() {
        return moreItemsAvailable;
    }
}
