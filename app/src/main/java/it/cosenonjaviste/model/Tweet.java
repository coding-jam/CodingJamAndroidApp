package it.cosenonjaviste.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;

@AutoValue
public abstract class Tweet implements Parcelable {
    public static Tweet create(long id, String text, Date createdAt, String userImage, String author) {
        return new AutoValue_Tweet(id, text, createdAt, userImage, author);
    }

    public abstract long id();

    public abstract String text();

    public abstract Date createdAt();

    public abstract String userImage();

    public abstract String author();

    public static TypeAdapter<Tweet> typeAdapter(Gson gson) {
        return new AutoValue_Tweet.GsonTypeAdapter(gson);
    }
}
