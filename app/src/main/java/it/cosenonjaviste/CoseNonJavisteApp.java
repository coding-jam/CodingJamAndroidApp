package it.cosenonjaviste;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import it.cosenonjaviste.lib.mvp.utils.ObjectsMapRetainedFragment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.Tweet;
import rx.functions.Func1;

@ParcelClasses({
        @ParcelClass(Post.class),
        @ParcelClass(Category.class),
        @ParcelClass(Tweet.class),
        @ParcelClass(Author.class)
})
public class CoseNonJavisteApp extends Application {

    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static ApplicationComponent getComponent(Fragment fragment) {
        return getComponent(fragment.getActivity());
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((CoseNonJavisteApp) context.getApplicationContext()).getComponent();
    }

    public void setComponent(ApplicationComponent component) {
        this.component = component;
    }

    public static <C> C createComponent(Fragment fragment, Func1<ApplicationComponent, C> componentFactory) {
        return ObjectsMapRetainedFragment.getOrCreate(
                fragment.getChildFragmentManager(),
                () -> componentFactory.call(getComponent(fragment.getActivity()))
        );
    }
}
