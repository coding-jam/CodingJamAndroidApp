package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class CategoryListPresenter extends RxMvpListPresenterAdapter<Category, CategoryListModel, CategoryListView> {

    @Inject WordPressService wordPressService;

    @Inject public CategoryListPresenter() {
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
                this::done,
                throwable -> error()
        );
    }

    public void goToPosts(int position) {
        Category category = getModel().get(position);
        getView().openPostList(new PostListModel(category));
    }
}
