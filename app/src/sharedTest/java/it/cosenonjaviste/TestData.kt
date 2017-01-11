package it.cosenonjaviste

import it.cosenonjaviste.model.*
import rx.Observable
import rx.functions.Func1
import java.util.*

object TestData {

    fun postResponse(size: Int): Observable<PostResponse> {
        return postResponse(0, size)
    }

    fun postResponse(start: Int, size: Int): Observable<PostResponse> {
        return Observable.range(start, size)
                .map<Post>(Func1<Int, Post> { createPost(it.toLong()) })
                .toList()
                .map<PostResponse>(Func1<List<Post>, PostResponse> { PostResponse.create(it) })
    }

    @JvmOverloads fun createPost(i: Long, url: String = "url " + i): Post {
        return Post.create(i, createAuthor(i), "post title " + i, Date(), url, "excerpt " + i)
    }

    fun createAuthor(i: Long): Author {
        return Author.create(i, "name " + i, "last name " + i, "email " + i)
    }

    fun authorResponse(size: Int): Observable<AuthorResponse> {
        return Observable.range(0, size)
                .map<Author>(Func1<Int, Author> { createAuthor(it.toLong()) })
                .toList()
                .map<AuthorResponse>(Func1<List<Author>, AuthorResponse> { AuthorResponse.create(it) })
    }

    fun categoryResponse(size: Int): Observable<CategoryResponse> {
        return Observable.range(0, size)
                .map<Category>(Func1<Int, Category> { createCategory(it) })
                .toList()
                .map<CategoryResponse>(Func1<List<Category>, CategoryResponse> { CategoryResponse.create(it) })
    }

    private fun createCategory(i: Int): Category {
        return Category.create(i.toLong(), "cat " + i, 10 + i)
    }

    fun tweets(count: Int): Observable<List<Tweet>> {
        return Observable.range(0, count)
                .map<Tweet>(Func1<Int, Tweet> { createTweet(it) })
                .toList()
    }

    fun createTweet(i: Int): Tweet {
        return Tweet.create(123, "tweet text " + i, Date(), "image", "author")
    }
}
