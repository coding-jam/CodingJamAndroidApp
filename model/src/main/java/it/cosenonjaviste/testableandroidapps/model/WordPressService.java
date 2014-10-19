package it.cosenonjaviste.testableandroidapps.model;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WordPressService {
    @GET("/?json=get_recent_posts&count=20&exclude=content,title_plain,excerpt,tags,custom_fields") Observable<PostResponse> listPosts(@Query("page") int page);

}