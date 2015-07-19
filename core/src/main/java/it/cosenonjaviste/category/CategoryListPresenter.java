package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;
import rx.functions.Action1;

@PresenterScope
public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel, CategoryListView> {

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
        loadData(b -> getModel().loadingPullToRefresh.set(b));
    }

    public void loadData() {
        loadData(b -> getModel().loading.set(b));
    }

    private void loadData(Action1<Boolean> loadingSetter) {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories)
                .finallyDo(() -> loadingSetter.call(false));

        subscribe(observable,
                () -> loadingSetter.call(true),
                posts -> getModel().done(posts),
                throwable -> getModel().error()
        );
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().openPostList(new PostListModel(category));
    }
}
