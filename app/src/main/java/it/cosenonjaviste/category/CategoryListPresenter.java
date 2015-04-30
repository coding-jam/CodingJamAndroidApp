package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

@PresenterScope
public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel, CategoryListFragment> {

    @Inject WordPressService wordPressService;

    @Inject public CategoryListPresenter() {
    }

    @Override public void resume() {
        super.resume();
        if (getModel().isEmpty() && !isTaskRunning()) {
            loadData();
        } else {
            getView().update(getModel());
        }
    }

    public void loadData() {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories);

        subscribe(observable,
                () -> {
                    getView().startLoading();
                },
                posts -> {
                    getModel().done(posts);
                    getView().update(getModel());
                }, throwable -> {
                    getModel().error(throwable);
                    getView().update(getModel());
                });
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().open(PostListFragment.class, new PostListModel(category));
    }
}
