package it.cosenonjaviste.model;

import java.util.List;

import rx.Observable;

public class PostService {
    private WordPressService wordPressService;

    public PostService(WordPressService wordPressService) {
        this.wordPressService = wordPressService;
    }

    public Observable<List<Post>> listPosts(int page) {
        return wordPressService.listPosts(page).map(PostResponse::getPosts);
    }
}
