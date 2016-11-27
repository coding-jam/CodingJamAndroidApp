package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.jakewharton.rxrelay.PublishRelay;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class CategoryListViewModel extends RxListViewModel<Void, CategoryListModel> {

    @Inject WordPressService wordPressService;

    public final PublishRelay<PostListArgument> postListNavigationEvents = PublishRelay.create();

    @Inject public CategoryListViewModel() {
    }

    @NonNull @Override protected CategoryListModel createModel() {
        return new CategoryListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingSetter) {
        Observable<List<Category>> observable = wordPressService
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
        postListNavigationEvents.call(PostListArgument.create(category));
    }
}
