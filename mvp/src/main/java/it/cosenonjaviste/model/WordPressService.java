package it.cosenonjaviste.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WordPressService {
    @GET("/?json=get_recent_posts&count=20&exclude=content,title_plain,tags,custom_fields&author_meta=email") Observable<PostResponse> listPosts(@Query("page") int page);

    @GET("/?json=get_author_index&author_meta=email") Observable<AuthorResponse> listAuthors();

    @GET("/?json=get_category_index") Observable<CategoryResponse> listCategories();
}