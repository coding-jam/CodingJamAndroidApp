package it.cosenonjaviste;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.Tweet;

public class TestData {

    public static Single<List<Post>> postResponse(int size) {
        return postResponse(0, size);
    }

    public static Single<List<Post>> postResponse(int start, int size) {
        return Observable.range(start, size)
                .map(TestData::createPost)
                .toList();
    }

    public static Post createPost(int i) {
        return createPost(i, "url " + i);
    }

    public static Post createPost(int i, String url) {
        return Post.create(i, createAuthor(i), "post title " + i, new Date(), url, "excerpt " + i);
    }

    public static Author createAuthor(int i) {
        return Author.create(i, "name " + i, "last name " + i, "email " + i);
    }

    public static Single<List<Author>> authorResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createAuthor)
                .toList();
    }

    public static Single<List<Category>> categoryResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createCategory)
                .toList();
    }

    private static Category createCategory(int i) {
        return Category.create(i, "cat " + i, 10 + i);
    }

    public static Single<List<Tweet>> tweets(int count) {
        return Observable.range(0, count)
                .map(TestData::createTweet)
                .toList();
    }

    public static Tweet createTweet(int i) {
        return Tweet.create(123, "tweet text " + i, new Date(), "image", "author");
    }
}
