package it.cosenonjaviste.core.model;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface MailJetService {

    @POST("/send/message") @FormUrlEncoded Observable<Response> sendEmail(
            @Field("from") String from,
            @Field("to") String to,
            @Field("subject") String subject,
            @Field("text") String text
    );
}
