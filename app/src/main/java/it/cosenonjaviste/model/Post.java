package it.cosenonjaviste.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapterFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import it.cosenonjaviste.ui.utils.DateFormatter;

@AutoValue
public abstract class Post implements Parcelable {
    public static Post create(long id, Author author, String title, Date date, String url, String excerpt, Attachment... attachments) {
        return new AutoValue_Post(id, author, title, date, url, excerpt, Arrays.asList(attachments));
    }

    public abstract long id();

    public abstract Author author();

    public abstract String title();

    @Nullable
    public abstract Date date();

    public abstract String url();

    @Nullable
    public abstract String excerpt();

    public abstract List<Attachment> attachments();

    public String excerptHtml() {
        String excerpt = excerpt();
        if (excerpt == null) {
            return "";
        }
        return excerpt.replaceAll("<br \\/><a .*>Continue reading...<\\/a>", "").replaceAll("^<p>", "").replaceAll("$</p>", "");
    }

    public String subtitle() {
        return author().name() + ", " + DateFormatter.formatDate(date());
    }

    public String imageUrl() {
        if (attachments() != null && !attachments().isEmpty()) {
            return attachments().get(0).url();
        } else {
            return null;
        }
    }

    public static TypeAdapterFactory typeAdapterFactory() {
        return AutoValue_Post.typeAdapterFactory();
    }
}
