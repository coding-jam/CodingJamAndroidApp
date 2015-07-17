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

@PresenterScope
public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel, CategoryListView> {

    @Inject WordPressService wordPressService;

    @Inject public CategoryListPresenter() {
    }

    @Override public void resume() {
        super.resume();
        if ((getModel().getItems() == null || getModel().getItems().isEmpty()) && !isTaskRunning()) {
            loadData();
        } else if (getModel().isError().get()) {
            getView().showError();
        } else {
            getView().update(getModel().getItems());
        }
    }

    public void loadData() {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories)
                .finallyDo(() -> getModel().loading.set(false));

        subscribe(observable,
                () -> getModel().loading.set(true),
                posts -> {
                    getModel().done(posts);
                    getView().update(getModel().getItems());
                }, throwable -> {
                    getModel().error();
                    getView().showError();
                });
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().openPostList(new PostListModel(category));
    }
}
