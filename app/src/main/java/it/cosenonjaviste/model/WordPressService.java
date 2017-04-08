package it.cosenonjaviste.model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WordPressService {

    int POST_PAGE_SIZE = 10;

    String POSTS_EXTRA = "&exclude=content,title_plain,tags,custom_fields,categories,comments&author_meta=email";
    String CATEGORY_POSTS_URL = "/?json=get_category_posts";
    String AUTHOR_POSTS_URL = "/?json=get_author_posts";

    @GET("/?json=get_recent_posts&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<PostResponse> listPosts(@Query("page") int page);

    @GET(CATEGORY_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<PostResponse> listCategoryPosts(@Query("id") long categoryId, @Query("page") int page);

    @GET(AUTHOR_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Single<PostResponse> listAuthorPosts(@Query("id") long authorId, @Query("page") int page);

    @GET("/?json=get_author_index&author_meta=email") Single<AuthorResponse> listAuthors();

    @GET("/?json=get_category_index") Single<CategoryResponse> listCategories();
}