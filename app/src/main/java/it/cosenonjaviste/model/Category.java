package it.cosenonjaviste.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Category implements Parcelable {

    public static Category create(long id, String title, int postCount) {
        return new AutoValue_Category(id, title, postCount);
    }

    @SerializedName("id")
    public abstract long getId();

    @SerializedName("title")
    public abstract String getTitle();

    @SerializedName("post_count")
    public abstract int getPostCount();

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Category.typeAdapterFactory();
    }
}
