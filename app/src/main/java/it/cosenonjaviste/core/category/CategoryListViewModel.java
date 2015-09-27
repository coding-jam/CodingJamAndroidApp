package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;

public class CategoryListViewModel extends RxListViewModel<Void, CategoryListModel> {

    private WordPressService wordPressService;

    private Navigator navigator;

    @Inject public CategoryListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService, Navigator navigator) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
        this.navigator = navigator;
        registerActivityAware(navigator);
    }

    @NonNull @Override protected CategoryListModel createModel() {
        return new CategoryListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingSetter) {
        Observable<List<Category>> observable = wordPressService
                .listCategories()
                .map(CategoryResponse::getCategories);

        subscribe(loadingSetter::set,
                observable,
                model::done,
                throwable -> model.error()
        );
    }

    public void goToPosts(int position) {
        Category category = model.get(position);
        navigator.openPostList(new PostListArgument(category));
    }
}
