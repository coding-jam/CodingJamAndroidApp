package it.cosenonjaviste.post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

@PresenterScope
public class PostListPresenter extends RxMvpPresenter<PostListModel, PostListFragment> {

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public PostListPresenter() {
    }

    @Override public void resume() {
        getView().update(model);
        rxHolder.resubscribePendingObservable();
        if (model.getItems().isEmpty() && !loadStarted) {
            reloadData();
        }
    }

    public void reloadData() {
        Observable<List<Post>> observable = getObservable(1);

        rxHolder.subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading(model.getItems().isEmpty());
                },
                posts -> {
                    model.getItems().done(new ArrayList<>(posts));
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    getView().update(model);
                }, throwable -> {
                    model.getItems().error(throwable);
                    getView().update(model);
                });
    }

    public void goToDetail(Post item) {
        getView().open(PageFragment.class, new PageModel(item.getUrl()));
    }

    public void loadNextPage() {
        int page = calcNextPage(model.getItems().size(), WordPressService.POST_PAGE_SIZE);
        Observable<List<Post>> observable = getObservable(page);

        Action0 onAttach = () -> getView().startMoreItemsLoading();
        Action1<? super List<Post>> onNext = posts -> {
            model.getItems().append(posts);
            model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
            getView().update(model);
        };
        Action1<Throwable> onError = throwable -> {
            model.getItems().error(throwable);
            getView().update(model);
        };
        rxHolder.subscribe(observable, onAttach, onNext, onError);
    }

    private Observable<List<Post>> getObservable(int page) {
        Observable<PostResponse> observable;
        Category category = model.getCategory();
        if (category != null) {
            observable = wordPressService.listCategoryPosts(category.getId(), page);
        } else {
            Author author = model.getAuthor();
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
