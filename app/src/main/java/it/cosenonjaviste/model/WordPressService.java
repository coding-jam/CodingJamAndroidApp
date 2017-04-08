package it.cosenonjaviste.model;

import java.util.List;

import io.reactivex.Single;
import it.cosenonjaviste.core.utils.EnvelopePayload;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WordPressService {

    int POST_PAGE_SIZE = 10;

    String POSTS_EXTRA = "&exclude=content,title_plain,tags,custom_fields,categories,comments&author_meta=email";
    String CATEGORY_POSTS_URL = "/?json=get_category_posts";
    String AUTHOR_POSTS_URL = "/?json=get_author_posts";

    @EnvelopePayload("posts")
    @GET("/?json=get_recent_posts&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<List<Post>> listPosts(@Query("page") int page);

    @EnvelopePayload("posts")
    @GET(CATEGORY_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<List<Post>> listCategoryPosts(@Query("id") long categoryId, @Query("page") int page);

    @EnvelopePayload("posts")
    @GET(AUTHOR_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<List<Post>> listAuthorPosts(@Query("id") long authorId, @Query("page") int page);

    @EnvelopePayload("authors")
    @GET("/?json=get_author_index&author_meta=email") Single<List<Author>> listAuthors();

    @EnvelopePayload("categories")
    @GET("/?json=get_category_index") Single<List<Category>> listCategories();
}