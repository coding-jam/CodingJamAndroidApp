package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.core.utils.Md5Utils;

@ParcelablePlease
public class Author implements Comparable<Author>, Parcelable {
    long id;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    String email;

    String imageUrl;

    String description;

    public Author() {
    }

    public Author(long id, String firstName, String lastName, String description) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        if (imageUrl == null && email != null && email.length() > 0) {
            imageUrl = "http://www.gravatar.com/avatar/" + Md5Utils.md5Hex(email);
        }
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getHtmlDescription() {
        return description.replaceAll("^<p>", "").replaceAll("$</p>", "");
    }

    @Override public int compareTo(Author o) {
        long lhs = getSortId();
        long rhs = o.getSortId();
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    private long getSortId() {
        if (id < 5 || id == 8 || id == 32) {
            return id;
        } else {
            return id + 100;
        }
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        AuthorParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        public Author createFromParcel(Parcel source) {
            Author target = new Author();
            AuthorParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
