package it.cosenonjaviste.model;

import it.cosenonjaviste.utils.Md5Utils;

public class Author {
    long id;

    String name;

    String email;

    String imageUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        if (imageUrl == null && email != null && email.length() > 0) {
            imageUrl = "http://www.gravatar.com/avatar/" + Md5Utils.md5Hex(email);
        }
        return imageUrl;
    }
}
