package it.cosenonjaviste.mvp.category;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class CategoryListMvpConfig extends MvpConfig<OptionalList<Category>, CategoryListView, CategoryListPresenter> {

    public CategoryListMvpConfig(Class<? extends CategoryListView> viewClass, CategoryListPresenter presenter) {
        super(viewClass, presenter);
    }
}
