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

    @Inject public PostListPresenter() {
    }

    @Override public void resume() {
        super.resume();
        if (getModel().getItems().isEmpty() && !isTaskRunning()) {
            reloadData();
        } else {
            getView().update(getModel());
        }
    }

    public void reloadData() {
        Observable<List<Post>> observable = getObservable(1);

        subscribe(observable,
                () -> getView().startLoading(getModel().getItems().isEmpty()),
                posts -> {
                    getModel().getItems().done(new ArrayList<>(posts));
                    getModel().setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    getView().update(getModel());
                }, throwable -> {
                    getModel().getItems().error(throwable);
                    getView().update(getModel());
                });
    }

    public void goToDetail(Post item) {
        getView().open(PageFragment.class, new PageModel(item.getUrl()));
    }

    public void loadNextPage() {
        int page = calcNextPage(getModel().getItems().size(), WordPressService.POST_PAGE_SIZE);
        Observable<List<Post>> observable = getObservable(page);

        Action0 onAttach = () -> getView().startMoreItemsLoading();
        Action1<? super List<Post>> onNext = posts -> {
            getModel().getItems().append(posts);
            getModel().setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
            getView().update(getModel());
        };
        Action1<Throwable> onError = throwable -> {
            getModel().getItems().error(throwable);
            getView().update(getModel());
        };
        subscribe(observable, onAttach, onNext, onError);
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
