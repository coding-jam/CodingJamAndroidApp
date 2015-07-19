package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

@PresenterScope
public class CategoryListPresenter extends RxMvpListPresenterAdapter<Category, CategoryListModel, CategoryListView> {

    @Inject WordPressService wordPressService;

    @Inject public CategoryListPresenter() {
    }

    @Override public CategoryListModel createDefaultModel() {
        return new CategoryListModel();
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !isTaskRunning()) {
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
