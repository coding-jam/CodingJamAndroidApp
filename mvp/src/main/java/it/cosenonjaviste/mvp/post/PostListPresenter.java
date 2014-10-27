package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.ListPresenter;
import rx.Observable;

public class PostListPresenter extends ListPresenter<Post> {

    @Inject WordPressService wordPressService;

    @Override protected Observable<List<Post>> getObservable(int page) {
        return wordPressService.listPosts(page).map(PostResponse::getPosts);
    }

    public void goToDetail(Post item) {
        PostDetailPresenter.open(contextBinder, item);
    }
}
