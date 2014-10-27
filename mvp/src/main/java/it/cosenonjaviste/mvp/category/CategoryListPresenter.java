package it.cosenonjaviste.mvp.category;

import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel> {
    @Override public CategoryListModel createModel(PresenterArgs args) {
        return new CategoryListModel();
    }
}
