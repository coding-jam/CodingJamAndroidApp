package it.cosenonjaviste.core.category;

import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;

public class CategoryListViewModel extends RxListViewModel<CategoryListModel> {

    private WordPressService wordPressService;

    private Navigator navigator;

    @Inject public CategoryListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService, Navigator navigator) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
        this.navigator = navigator;
        registerActivityAware(navigator);
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
        navigator.openPostList(new PostListModel(category));
    }
}
