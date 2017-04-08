package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;

public class CategoryListViewModel extends RxListViewModel<Void, CategoryListModel> {

    @Inject WordPressService wordPressService;

    @Inject @BindLifeCycle Navigator navigator;

    @Inject public CategoryListViewModel() {
    }

    @NonNull @Override protected CategoryListModel createModel() {
        return new CategoryListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingSetter) {
        Single<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::categories);

        subscribe(loadingSetter::set,
                observable,
                model::done,
                throwable -> model.error()
        );
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        navigator.openPostList(PostListArgument.create(category));
    }
}
