package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class CategoryListPresenter extends RxMvpListPresenterAdapter<CategoryListModel, CategoryListView> {

    private WordPressService wordPressService;

    @Inject public CategoryListPresenter(SchedulerManager schedulerManager, WordPressService wordPressService) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
    }

    @Override public CategoryListModel createDefaultModel() {
        return new CategoryListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingSetter) {
        loadingSetter.set(true);

        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories)
                .finallyDo(() -> loadingSetter.set(false));

        subscribe(observable,
                l -> getModel().done(l),
                throwable -> getModel().error()
        );
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().openPostList(new PostListModel(category));
    }
}
