package it.cosenonjaviste.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Attachment implements Parcelable {
    public static Attachment create(String url) {
        return new AutoValue_Attachment(url);
    }

    public abstract String url();

    public static TypeAdapter<Attachment> typeAdapter(Gson gson) {
        return new AutoValue_Attachment.GsonTypeAdapter(gson);
    }
}
