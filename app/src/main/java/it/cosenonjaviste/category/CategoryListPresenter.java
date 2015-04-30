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

    private boolean loadStarted;

    @Inject public CategoryListPresenter() {
    }

    @Override public void resume() {
        getView().update(model);
        rxHolder.resubscribePendingObservable();
        if (model.isEmpty() && !loadStarted) {
            loadData();
        }
    }

    public void loadData() {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories);

        rxHolder.subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading();
                },
                posts -> {
                    model.done(posts);
                    getView().update(model);
                }, throwable -> {
                    model.error(throwable);
                    getView().update(model);
                });
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(category));
    }
}
