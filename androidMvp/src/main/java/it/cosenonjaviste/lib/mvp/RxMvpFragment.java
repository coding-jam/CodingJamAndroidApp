package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.ObjectsMapRetainedFragment;
import rx.functions.Func0;

public abstract class RxMvpFragment<M> extends Fragment implements MvpView<M> {

    public static final String MODEL = "model";

    @Inject LifeCycle lifeCycle;

    private Object model;

    @Override public final void onCreate(Bundle state) {
        super.onCreate(state);
        model = init(state);
    }

    protected abstract Object init(Bundle state);

    protected static <M> M getRestoredModel(Bundle state, Bundle arguments) {
        M restoredModel = null;
        if (state != null) {
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (restoredModel == null && arguments != null) {
            restoredModel = Parcels.unwrap(arguments.getParcelable(MODEL));
        }
        return restoredModel;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (model != null) {
            outState.putParcelable(MODEL, Parcels.wrap(model));
        }
    }

    @Override public void onResume() {
        super.onResume();
        lifeCycle.emit(LifeCycle.EventType.RESUME);
    }

    @Override public void onPause() {
        super.onPause();
        lifeCycle.emit(LifeCycle.EventType.PAUSE);
    }

    protected <T> T createComponent(Func0<T> componentFactory) {
        return ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                componentFactory
        );
    }
}
