package it.cosenonjaviste.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    @Inject WordPressService wordPressService;

    private boolean loadStarted;

    @Inject public PostListPresenter(SchedulerManager schedulerManager) {
        super(schedulerManager);
    }

    @Override public void subscribe(MvpView<PostListModel> view) {
        super.subscribe(view);
        if (model.getItems().isEmpty() && !loadStarted) {
            reloadData();
        }
    }

    public void reloadData() {
        Observable<List<Post>> observable = getObservable(0);

        subscribe(observable,
                () -> {
                    loadStarted = true;
                    getView().startLoading(model.getItems().isEmpty());
                },
                posts -> {
                    model.getItems().done(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.getItems().error(throwable);
                    view.update(model);
                });
    }

    public void goToDetail(Post item) {
        getView().open(PageFragment.class, new PageModel(item.getUrl()));
    }

    public void loadNextPage() {
        int page = calcNextPage(model.getItems().size(), WordPressService.POST_PAGE_SIZE);
        Observable<List<Post>> observable = getObservable(page);

        subscribe(observable,
                () -> getView().startMoreItemsLoading(),
                posts -> {
                    model.getItems().append(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.getItems().error(throwable);
                    view.update(model);
                });
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

    @Override public PostListFragment getView() {
        return (PostListFragment) super.getView();
    }
}