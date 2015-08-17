package it.cosenonjaviste.lib;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ViewModelManager {
    public static final String MODEL = "model";

    public static void resume(MvpFragment<?> mvpFragment, MvpPresenter<?, ?> listener) {
        ((MvpPresenter) listener).resume(mvpFragment);
    }

    public static void pause(MvpPresenter<?, ?> listener) {
        listener.pause();
    }

    public static void destroy(MvpPresenter<?, ?> listener) {
        listener.detachView();
    }

    public static void saveState(final Bundle outState, MvpPresenter<?, ?> listener) {
        outState.putParcelable(MODEL, listener.getModel());
    }

    @NonNull public static <P extends MvpPresenter<?, ?>> P init(P presenter, Bundle state, Bundle args, Factory<P> factory) {
        if (presenter == null) {
            presenter = factory.create();
        }
        Parcelable model = null;
        if (state != null) {
            model = state.getParcelable(MODEL);
        }
        if (model == null && args != null) {
            model = args.getParcelable(MODEL);
        }
        ((MvpPresenter) presenter).initModel(model);
        return presenter;
    }

    public interface Factory<P extends MvpPresenter<?, ?>> {
        P create();
    }
}
