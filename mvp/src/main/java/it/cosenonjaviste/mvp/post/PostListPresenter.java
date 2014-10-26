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

    public PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        Observable<List<Post>> observable = wordPressService.listPosts(page).map(PostResponse::getPosts);

        subscribePausable(observable,
                () -> getView().startLoading(),
                posts -> {
                    model.getPosts().done(posts);
                    view.update(model);
                }, throwable -> {
                    model.getPosts().error(throwable);
                    view.update(model);
                });
    }

    public void goToDetail(Post item) {
        PostDetailPresenter.open(contextBinder, item);
    }

    @Override public PostListView getView() {
        return (PostListView) super.getView();
    }
}
