package it.cosenonjaviste.testableandroidapps.model;

import java.util.List;

import rx.Observable;

public class RepoService {
    private WordPressService wordPressService;

    public RepoService(WordPressService wordPressService) {
        this.wordPressService = wordPressService;
    }

    public Observable<List<Post>> listPosts() {
        return wordPressService.listPosts().map(PostResponse::getPosts);
    }
}
