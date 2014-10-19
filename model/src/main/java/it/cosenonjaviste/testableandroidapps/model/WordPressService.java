package it.cosenonjaviste.testableandroidapps.model;

import retrofit.http.GET;
import rx.Observable;

public interface WordPressService {
    @GET("/?json=get_recent_posts&exclude=content,title_plain,excerpt,tags,custom_fields") Observable<PostResponse> listPosts();

}