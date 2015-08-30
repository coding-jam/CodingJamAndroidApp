package it.cosenonjaviste.core;

import java.util.Date;
import java.util.List;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.CategoryResponse;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.Tweet;
import rx.Observable;

public class TestData {

    public static Observable<PostResponse> postResponse(int size) {
        return postResponse(0, size);
    }

    public static Observable<PostResponse> postResponse(int start, int size) {
        return Observable.range(start, size)
                .map(TestData::createPost)
                .toList()
                .map(PostResponse::new);
    }

    public static Post createPost(int i) {
        return createPost(i, "url " + i);
    }

    public static Post createPost(int i, String url) {
        return new Post(i, createAuthor(i), "post title " + i, new Date(), url, "excerpt " + i);
    }

    public static Author createAuthor(int i) {
        return new Author(i, "name " + i, "last name " + i);
    }

    public static Observable<AuthorResponse> authorResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createAuthor)
                .toList()
                .map(AuthorResponse::new);
    }

    public static Observable<CategoryResponse> categoryResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createCategory)
                .toList()
                .map(CategoryResponse::new);
    }

    private static Category createCategory(int i) {
        return new Category(i, "cat " + i, 10 + i);
    }

    public static Observable<List<Tweet>> tweets() {
        return Observable.range(0, 10)
                .map(TestData::createTweet)
                .toList();
    }

    public static Tweet createTweet(int i) {
        return new Tweet(123, "tweet text " + i, new Date(), "image", "author");
    }
}
