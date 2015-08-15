package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Category implements Parcelable {
    long id;

    String title;

    @SerializedName("post_count")
    int postCount;

    public Category() {
    }

    public Category(long id, String title, int postCount) {
        this();
        this.id = id;
        this.title = title;
        this.postCount = postCount;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPostCount() {
        return postCount;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        CategoryParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            Category target = new Category();
            CategoryParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
