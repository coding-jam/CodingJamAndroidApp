package it.cosenonjaviste.core.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WordPressService {

    int POST_PAGE_SIZE = 10;

    String POSTS_EXTRA = "&exclude=content,title_plain,tags,custom_fields,categories,comments&author_meta=email";
    String CATEGORY_POSTS_URL = "/?json=get_category_posts";
    String AUTHOR_POSTS_URL = "/?json=get_author_posts";

    @GET("/?json=get_recent_posts&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Observable<PostResponse> listPosts(@Query("page") int page);

    @GET(CATEGORY_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Observable<PostResponse> listCategoryPosts(@Query("id") long categoryId, @Query("page") int page);

    @GET(AUTHOR_POSTS_URL + "&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Observable<PostResponse> listAuthorPosts(@Query("id") long authorId, @Query("page") int page);

    @GET("/?json=get_author_index&author_meta=email") Observable<it.cosenonjaviste.core.model.AuthorResponse> listAuthors();

    @GET("/?json=get_category_index") Observable<CategoryResponse> listCategories();
}