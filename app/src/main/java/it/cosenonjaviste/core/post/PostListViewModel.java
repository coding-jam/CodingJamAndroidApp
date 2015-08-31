package it.cosenonjaviste.core.post;

import android.databinding.ObservableBoolean;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.lib.rx.SchedulerManager;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class PostListViewModel extends RxListViewModel<PostListModel, PostListView> {

    private WordPressService wordPressService;

    @Inject public PostListViewModel(SchedulerManager schedulerManager, WordPressService wordPressService) {
        super(schedulerManager);
        this.wordPressService = wordPressService;
    }

    @Override public PostListModel createDefaultModel() {
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
        getView().openDetail(new PageModel(item));
    }

    public void loadNextPage() {
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

    private Observable<List<Post>> getObservable(int page) {
        Observable<PostResponse> observable;
        Category category = getModel().getCategory();
        if (category != null) {
            observable = wordPressService.listCategoryPosts(category.getId(), page);
        } else {
            Author author = getModel().getAuthor();
            if (author != null) {
                observable = wordPressService.listAuthorPosts(author.getId(), page);
            } else {
                observable = wordPressService.listPosts(page);
            }
        }
        return observable.map(PostResponse::getPosts);
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }
}
