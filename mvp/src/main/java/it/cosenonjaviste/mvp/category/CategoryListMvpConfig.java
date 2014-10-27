package it.cosenonjaviste.mvp.category;

import javax.inject.Inject;
import javax.inject.Provider;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class CategoryListMvpConfig extends MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> {

    @Inject Provider<CategoryListView> viewProvider;

    @Inject CategoryListPresenter presenter;

    @Override public CategoryListView createView() {
        return viewProvider.get();
    }

    @Override protected CategoryListPresenter createPresenter() {
        return presenter;
    }
}
