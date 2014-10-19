package it.cosenonjaviste.mvp;

import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.EndLoadingModelEvent;
import it.cosenonjaviste.mvp.base.events.ErrorModelEvent;
import it.cosenonjaviste.mvp.base.events.StartLoadingModelEvent;
import it.cosenonjaviste.testableandroidapps.model.PostService;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    private PostService postService;

    public PostListPresenter(PostService postService) {
        this.postService = postService;
    }

    protected PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        subscribePausable(postService.listPosts(page),
                () -> publish(new StartLoadingModelEvent<>(model)),
                posts -> {
                    model.setReloadVisible(false);
                    model.setPosts(posts);
                    publish(new EndLoadingModelEvent<>(model));
                }, throwable -> {
                    model.setReloadVisible(true);
                    publish(new ErrorModelEvent<>(model, throwable));
                });
    }

    @Override protected void loadOnFirstStart() {
        listPosts(0);
    }
}
