package it.cosenonjaviste.core.post;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;

@AutoValue
public abstract class PostListArgument implements Parcelable {

    public static PostListArgument create(Category category) {
        return new AutoValue_PostListArgument(category, null);
    }

    public static PostListArgument create(Author author) {
        return new AutoValue_PostListArgument(null, author);
    }

    @Nullable public abstract Category category();

    @Nullable public abstract Author author();
}
