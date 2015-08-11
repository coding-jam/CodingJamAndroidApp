package it.cosenonjaviste.core.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.core.model.Category;
import it.cosenonjaviste.core.model.CategoryResponse;
import it.cosenonjaviste.core.model.WordPressService;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import rx.Observable;

public class CategoryListPresenter extends RxMvpListPresenterAdapter<Category, it.cosenonjaviste.core.category.CategoryListModel, CategoryListView> {

    @Inject WordPressService wordPressService;

    @Inject public CategoryListPresenter() {
    }

    @Override public it.cosenonjaviste.core.category.CategoryListModel createDefaultModel() {
        return new it.cosenonjaviste.core.category.CategoryListModel();
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !loading.get()) {
            loadData();
        }
    }

    public void loadDataPullToRefresh() {
        loadData(loadingPullToRefresh);
    }

    public void loadData() {
        loadData(loading);
    }

    private void loadData(BindableBoolean loadingSetter) {
        loadingSetter.set(true);

        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories)
                .finallyDo(() -> loadingSetter.set(false));

        subscribe(observable,
                this::done,
                throwable -> error()
        );
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().openPostList(new PostListModel(category));
    }
}
