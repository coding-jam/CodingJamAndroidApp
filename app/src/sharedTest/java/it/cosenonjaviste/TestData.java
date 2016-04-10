package it.cosenonjaviste;

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
                .map(PostResponse::create);
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

    public static Observable<AuthorResponse> authorResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createAuthor)
                .toList()
                .map(AuthorResponse::create);
    }

    public static Observable<CategoryResponse> categoryResponse(int size) {
        return Observable.range(0, size)
                .map(TestData::createCategory)
                .toList()
                .map(CategoryResponse::create);
    }

    private static Category createCategory(int i) {
        return Category.create(i, "cat " + i, 10 + i);
    }

    public static Observable<List<Tweet>> tweets(int count) {
        return Observable.range(0, count)
                .map(TestData::createTweet)
                .toList();
    }

    public static Tweet createTweet(int i) {
        return Tweet.create(123, "tweet text " + i, new Date(), "image", "author");
    }
}
