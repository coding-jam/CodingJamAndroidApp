package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

@ParcelablePlease
public class Tweet implements Parcelable {
    long id;
    String text;
    Date createdAt;
    String userImage;
    String author;

    public Tweet() {
    }

    public Tweet(long id, String text, Date createdAt, String userImage, String author) {
        this();
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.userImage = userImage;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getAuthor() {
        return author;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        TweetParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            Tweet target = new Tweet();
            TweetParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
