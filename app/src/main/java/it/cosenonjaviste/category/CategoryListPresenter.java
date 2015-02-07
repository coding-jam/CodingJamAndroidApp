package it.cosenonjaviste.category;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;

public class CategoryListPresenter extends RxMvpPresenter<OptionalList<Category>> {

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public CategoryListPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    @Override public void subscribe(MvpView<OptionalList<Category>> view) {
        super.subscribe(view);
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
}
