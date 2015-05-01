package it.cosenonjaviste;

import android.app.Application;
import android.content.Context;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.Tweet;

@ParcelClasses({
        @ParcelClass(Post.class),
        @ParcelClass(Category.class),
        @ParcelClass(Tweet.class),
        @ParcelClass(Author.class)
})
public class CoseNonJavisteApp extends Application {

    public static ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((CoseNonJavisteApp) context.getApplicationContext()).getComponent();
    }
}
