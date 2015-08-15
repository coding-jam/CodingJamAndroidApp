package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

import it.cosenonjaviste.ui.utils.DateFormatter;

@ParcelablePlease
public class Post implements Parcelable {
    long id;
    Author author;
    String title;
    Date date;
    String url;
    String excerpt;
    Attachment[] attachments;

    public Post() {
    }

    public Post(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Post(long id, Author author, String title, Date date, String url, String excerpt) {
        this();
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.url = url;
        this.excerpt = excerpt;
    }

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public String getExcerptHtml() {
        if (excerpt == null) {
            return "";
        }
        return excerpt.replaceAll("^<p>", "").replaceAll("$</p>", "");
    }

    public String getSubtitle() {
        return author.getName() + ", " + DateFormatter.formatDate(date);
    }

    public String getImageUrl() {
        if (attachments != null && attachments.length > 0) {
            return attachments[0].getUrl();
        } else {
            return null;
        }
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        PostParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            Post target = new Post();
            PostParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
