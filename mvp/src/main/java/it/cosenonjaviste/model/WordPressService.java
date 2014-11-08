package it.cosenonjaviste.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WordPressService {

    int POST_PAGE_SIZE = 10;

    @GET("/?json=get_recent_posts&count=" + POST_PAGE_SIZE + "&exclude=content,title_plain,tags,custom_fields&author_meta=email") Observable<PostResponse> listPosts(@Query("page") int page);

    @GET("/?json=get_author_index&author_meta=email") Observable<AuthorResponse> listAuthors();

    @GET("/?json=get_category_index") Observable<CategoryResponse> listCategories();
}