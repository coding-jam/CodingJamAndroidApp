package it.cosenonjaviste.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import it.cosenonjaviste.core.utils.Md5Utils;

@AutoValue
public abstract class Author implements Comparable<Author>, Parcelable {

    public static Author create(long id, String firstName, String lastName, String email) {
        return new AutoValue_Author(id, firstName, lastName, email);
    }

    public static TypeAdapter<Author> typeAdapter(Gson gson) {
        return new AutoValue_Author.GsonTypeAdapter(gson);
    }

    public abstract long id();

    @SerializedName("first_name")
    public abstract String firstName();

    @SerializedName("last_name")
    public abstract String lastName();

    public abstract String email();

    public String name() {
        return firstName() + " " + lastName();
    }

    public String imageUrl() {
        if (email() != null && email().length() > 0) {
            return "http://www.gravatar.com/avatar/" + Md5Utils.md5Hex(email());
        }
        return null;
    }

    @Override public int compareTo(Author o) {
        long lhs = sortId();
        long rhs = o.sortId();
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    private long sortId() {
        long id = id();
        if (id < 5 || id == 8 || id == 32) {
            return id;
        } else {
            return id + 100;
        }
    }
}
