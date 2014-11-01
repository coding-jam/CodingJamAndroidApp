package it.cosenonjaviste.mvp.category;

import javax.inject.Provider;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class CategoryListMvpConfig extends MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> {

    public CategoryListMvpConfig(Class<? extends CategoryListView> viewClass, Provider<CategoryListPresenter> presenter) {
        super(viewClass, presenter::get);
    }
}
