package it.cosenonjaviste.mvp.base;

import java.util.ArrayList;
import java.util.List;

public class ListModel {
    ListInfo listInfo = new ListInfo();

    public ListInfo getListInfo() {
        return listInfo;
    }

    public int getLastLoadedPage() {
        return listInfo.getLastLoadedPage();
    }

    public void setLastLoadedPage(int lastLoadedPage) {
        listInfo.setLastLoadedPage(lastLoadedPage);
    }

    protected <T> List<T> updateItems(List<T> currentList, Integer page, List<T> items) {
        listInfo.done(page, items);
        setLastLoadedPage(page == null ? 0 : page);
        if (page == null || page == 0) {
            return new ArrayList<>(items);
        } else {
            currentList.addAll(items);
        }
        return currentList;
    }

    public void start(Integer page) {
        listInfo.start(page);
    }

    public void error(Integer param, Throwable t1) {
        listInfo.error(param, t1);
    }
}
