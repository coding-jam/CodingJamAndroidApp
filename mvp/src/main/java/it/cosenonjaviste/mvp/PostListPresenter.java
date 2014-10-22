package it.cosenonjaviste.mvp;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.EndLoadingModelEvent;
import it.cosenonjaviste.mvp.base.events.ErrorModelEvent;
import it.cosenonjaviste.mvp.base.events.StartLoadingModelEvent;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    @Inject PostService postService;

    protected PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        Observable<List<Post>> observable = postService.listPosts(page);

        subscribePausable(observable,
                () -> publish(new StartLoadingModelEvent<>(model)),
                posts -> {
                    model.getPostsModel().done(posts);
                    publish(new EndLoadingModelEvent<>(model));
                }, throwable -> {
                    model.getPostsModel().error(throwable);
                    publish(new ErrorModelEvent<>(model, throwable));
                });
    }

    @Override protected void loadOnFirstStart() {
        listPosts(0);
    }

    public void goToDetail(Post item) {

    }
}
