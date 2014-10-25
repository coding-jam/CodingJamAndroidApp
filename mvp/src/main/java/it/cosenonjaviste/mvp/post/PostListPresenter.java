package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import rx.Observable;

public class PostListPresenter extends RxMvpPresenter<PostListModel> {

    @Inject WordPressService wordPressService;

    protected PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        Observable<List<Post>> observable = wordPressService.listPosts(page).map(PostResponse::getPosts);

        subscribePausable(observable,
                () -> getView().startLoading(),
                posts -> {
                    model.getPostsModel().done(posts);
                    view.update(model);
                }, throwable -> {
                    model.getPostsModel().error(throwable);
                    view.update(model);
                });
    }

    @Override protected void loadOnFirstStart() {
        listPosts(0);
    }

    public void goToDetail(Post item) {
        PresenterArgs args = PostDetailPresenter.populateArgs(contextBinder.createArgs(), item);
        contextBinder.startNewActivity(PostDetailMvpConfig.class, args);
    }

    @Override public PostListView getView() {
        return (PostListView) super.getView();
    }
}
