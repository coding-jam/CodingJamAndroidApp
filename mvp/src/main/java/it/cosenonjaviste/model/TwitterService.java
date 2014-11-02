package it.cosenonjaviste.model;

import java.util.List;

import rx.Observable;

public interface TwitterService {
    Observable<List<Tweet>> loadTweets();
}
