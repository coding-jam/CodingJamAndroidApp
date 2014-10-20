package it.cosenonjaviste.model;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PostService {
    @Inject WordPressService wordPressService;

    public Observable<List<Post>> listPosts(int page) {
        return wordPressService.listPosts(page).map(PostResponse::getPosts);
    }
}
