package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    private static final String CATEGORY = "category";

    @Inject WordPressService wordPressService;

    @Inject MvpConfig<PostDetailView, PostDetailPresenter> postDetailMvpConfig;

    @Override public PostListModel createModel(PresenterArgs args) {
        PostListModel postListModel = new PostListModel();
        postListModel.setCategory(args.getObject(CATEGORY));
        return postListModel;
    }

    public void reloadData() {
        Observable<List<Post>> observable = wordPressService
                .listPosts(0)
                .map(PostResponse::getPosts);

        subscribePausable(observable,
                () -> getView().startLoading(model.isEmpty()),
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public void goToDetail(Post item) {
        PostDetailPresenter.open(contextBinder, postDetailMvpConfig, item);
    }

    public static PresenterArgs open(ContextBinder contextBinder, Category category) {
        PresenterArgs args = contextBinder.createArgs();
        args.putObject(CATEGORY, category);
        return args;
    }

    public void loadNextPage() {
        Observable<List<Post>> observable = wordPressService
                .listPosts(calcNextPage(model.size(), WordPressService.POST_PAGE_SIZE))
                .map(PostResponse::getPosts);

        subscribePausable(observable,
                () -> getView().startMoreItemsLoading(),
                posts -> {
                    model.append(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    @Override public PostListView getView() {
        return (PostListView) super.getView();
    }
}
