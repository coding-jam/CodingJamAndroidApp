package it.cosenonjaviste.mvp.category;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.RxMvpView;

public interface CategoryListView extends RxMvpView<Category> {
    void startLoading();
}
