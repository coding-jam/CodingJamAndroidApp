package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvp2Presenter;
import rx.Observable;

public class PostListPresenter2 extends RxMvp2Presenter<PostListModel, PostListView> {

    @Inject WordPressService wordPressService;

    protected PostListModel createModel(PresenterArgs args) {
        return new PostListModel();
    }

    public void listPosts(int page) {
        Observable<List<Post>> observable = wordPressService.listPosts(page).map(PostResponse::getPosts);

        subscribePausable(observable,
                () -> view.startLoading(),
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
//        navigator.show(contextBinder, PostDetailPresenter.class, args -> PostDetailPresenter.populateArgs(args, item));
    }

}
