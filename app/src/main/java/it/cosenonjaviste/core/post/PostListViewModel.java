package it.cosenonjaviste.core.post;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mv2m.rx.SchedulerManager;
import rx.Observable;

public class PostListViewModel extends RxListViewModel<PostListArgument, PostListModel> {

    private WordPressService wordPressService;

    private Navigator navigator;

    @Inject public PostListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService, Navigator navigator) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
        this.navigator = navigator;
        registerActivityAware(navigator);
    }

    @NonNull @Override protected PostListModel createModel() {
        return new PostListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        loadingAction.set(true);
        Observable<List<Post>> observable = getObservable(1).finallyDo(() -> loadingAction.set(false));

        subscribe(observable,
                posts -> {
                    getModel().done(posts);
                    getModel().setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                }, throwable -> getModel().error());
    }

    public void goToDetail(Post item) {
        navigator.openDetail(item);
    }

    public void loadNextPage() {
        if (!loadingNextPage.get() && getModel().isMoreDataAvailable()) {
            loadingNextPage.set(true);
            int page = calcNextPage(getModel().getItems().size(), WordPressService.POST_PAGE_SIZE);
            Observable<List<Post>> observable = getObservable(page).finallyDo(() -> loadingNextPage.set(false));

            subscribe(observable,
                    posts -> {
                        getModel().append(posts);
                        getModel().setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    },
                    throwable -> getModel().error());
        }
    }

    private Observable<List<Post>> getObservable(int page) {
        Observable<PostResponse> observable;
        if (getArgument() == null) {
            observable = wordPressService.listPosts(page);
        } else {
            Category category = getArgument().getCategory();
            if (category != null) {
                observable = wordPressService.listCategoryPosts(category.getId(), page);
            } else {
                Author author = getArgument().getAuthor();
                observable = wordPressService.listAuthorPosts(author.getId(), page);
            }
        }
        return observable.map(PostResponse::getPosts);
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    public boolean isToolbarVisible() {
        PostListArgument arg = getArgument();
        return arg != null && (arg.getAuthor() != null || arg.getCategory() != null);
    }

    public String getToolbarTitle() {
        PostListArgument arg = getArgument();
        if (arg == null) {
            return null;
        } else {
            Author author = arg.getAuthor();
            if (author != null) {
                return author.getName();
            } else {
                Category category = arg.getCategory();
                if (category != null) {
                    return category.getTitle();
                } else {
                    return null;
                }
            }
        }
    }
}
