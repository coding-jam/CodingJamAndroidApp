package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

public abstract class Dagger2MvpFragment<M> extends Fragment implements MvpView<M> {

    public static final String MODEL = "model";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        M restoredModel = null;
        if (state != null) {
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (restoredModel == null && getArguments() != null) {
            restoredModel = Parcels.unwrap(getArguments().getParcelable(MODEL));
        }

        getPresenter().init(restoredModel);
    }

    public abstract MvpPresenter<M> getPresenter();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(getPresenter().getModel()));
    }

    @Override public void onStart() {
        super.onStart();
        getPresenter().subscribe(this);
    }

    @Override public void onStop() {
        getPresenter().pause();
        super.onStop();
    }

}
