package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

public abstract class RxMvpFragment<M> extends Fragment implements MvpView<M> {

    public static final String MODEL = "model";
    private LifeCycle lifeCycle;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        M restoredModel = null;
        if (state != null) {
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (restoredModel == null && getArguments() != null) {
            restoredModel = Parcels.unwrap(getArguments().getParcelable(MODEL));
        }

        getPresenter().init(restoredModel, this);
        lifeCycle = getPresenter().getLifeCycle();
    }

    public abstract RxMvpPresenter<M> getPresenter();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifeCycle.saveInstanceState(obj -> {
            if (obj != null) {
                outState.putParcelable(MODEL, Parcels.wrap(obj));
            }
        });
    }

    @Override public void onResume() {
        super.onResume();
        lifeCycle.emit(LifeCycle.EventType.RESUME);
    }

    @Override public void onPause() {
        super.onPause();
        lifeCycle.emit(LifeCycle.EventType.PAUSE);
    }
}
