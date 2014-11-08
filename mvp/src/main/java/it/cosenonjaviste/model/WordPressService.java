package it.cosenonjaviste.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WordPressService {

    int POST_PAGE_SIZE = 10;

    String POSTS_EXTRA = "&exclude=content,title_plain,tags,custom_fields&author_meta=email";

    @GET("/?json=get_recent_posts&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Observable<PostResponse> listPosts(@Query("page") int page);

    @GET("/?json=get_category_posts&count=" + POST_PAGE_SIZE + POSTS_EXTRA) Observable<PostResponse> listCategoryPosts(@Query("id") long categoryId, @Query("page") int page);

    @GET("/?json=get_author_index&author_meta=email") Observable<AuthorResponse> listAuthors();

    @GET("/?json=get_category_index") Observable<CategoryResponse> listCategories();
}