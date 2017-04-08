package it.cosenonjaviste.model;


import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MailJetService {

    @POST("/send/message") @FormUrlEncoded Completable sendEmail(
            @Field("from") String from,
            @Field("to") String to,
            @Field("subject") String subject,
            @Field("text") String text
    );
}
