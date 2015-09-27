package it.cosenonjaviste.core.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;

@ParcelablePlease
public class PostListArgument implements Parcelable {

    Category category;

    Author author;

    public PostListArgument(Category category) {
        this.category = category;
    }

    public PostListArgument(Author author) {
        this.author = author;
    }

    public PostListArgument() {
    }

    public Category getCategory() {
        return category;
    }

    public Author getAuthor() {
        return author;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        PostListArgumentParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PostListArgument> CREATOR = new Creator<PostListArgument>() {
        public PostListArgument createFromParcel(Parcel source) {
            PostListArgument target = new PostListArgument();
            PostListArgumentParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PostListArgument[] newArray(int size) {
            return new PostListArgument[size];
        }
    };
}
