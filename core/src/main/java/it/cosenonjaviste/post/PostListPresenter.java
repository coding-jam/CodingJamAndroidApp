package it.cosenonjaviste.post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageModel;
import rx.Observable;
import rx.functions.Action1;

public class PostListPresenter extends RxMvpListPresenterAdapter<Post, PostListModel, PostListView> {

    @Inject WordPressService wordPressService;

    @Inject public PostListPresenter() {
    }

    @Override public PostListModel createDefaultModel() {
        return new PostListModel();
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !loading.get()) {
            reloadData();
        }
    }

    public void loadDataPullToRefresh() {
        reloadData(loadingPullToRefresh::set);
    }

    public void reloadData() {
        reloadData(loading::set);
    }

    public void reloadData(Action1<Boolean> loadingAction) {
        loadingAction.call(true);
        Observable<List<Post>> observable = getObservable(1).finallyDo(() -> loadingAction.call(false));

        subscribe(observable,
                posts -> {
                    done(new ArrayList<>(posts));
                    getModel().setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                }, throwable -> error());
    }

    public void goToDetail(Post item) {
        getView().openDetail(new PageModel(item.getUrl()));
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
                throwable -> error());
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
