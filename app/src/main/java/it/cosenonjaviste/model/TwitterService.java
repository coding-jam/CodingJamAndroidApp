package it.cosenonjaviste.model;

import java.util.List;

import rx.Observable;

public interface TwitterService {

    int PAGE_SIZE = 20;

    Observable<List<Tweet>> loadTweets(int page);
}
