package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.EndLoadingModelEvent;
import it.cosenonjaviste.mvp.base.events.ErrorModelEvent;
import it.cosenonjaviste.mvp.base.events.StartLoadingModelEvent;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    @Inject WordPressService wordPressService;

    @Inject Navigator navigator;

    protected PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        Observable<List<Post>> observable = wordPressService.listPosts(page).map(PostResponse::getPosts);

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
        navigator.show(contextBinder, PostDetailPresenter.class, args -> PostDetailPresenter.populateArgs(args, item));
    }

}
