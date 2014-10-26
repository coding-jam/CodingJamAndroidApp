package it.cosenonjaviste.model;

import com.google.gson.annotations.SerializedName;

import it.cosenonjaviste.utils.Md5Utils;

public class Author implements Comparable<Author> {
    long id;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    String email;

    String imageUrl;

    String description;

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

    @Override public int compareTo(Author o) {
        return Long.compare(id, o.id);
    }
}
