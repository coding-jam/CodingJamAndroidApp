package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

@PresenterScope
public class CategoryListPresenter extends RxMvpPresenter<CategoryListModel> {

    private CategoryListModel model;

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public CategoryListPresenter() {
    }

    @Override public void resume() {
        view.update(model);
        rxHolder.resubscribePendingObservable();
        if (model.isEmpty() && !loadStarted) {
            loadData();
        }
    }

    public void loadData() {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories);

        subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading();
                },
                posts -> {
                    model.done(posts);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        getView().open(PostListFragment.class, new PostListModel(category));
    }

    @Override public CategoryListFragment getView() {
        return (CategoryListFragment) super.getView();
    }

    public void init(it.cosenonjaviste.category.CategoryListModel model, MvpView<CategoryListModel> view) {
        this.model = model;
        this.view = view;
    }
}
