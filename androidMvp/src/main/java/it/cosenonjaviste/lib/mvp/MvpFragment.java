package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.lib.mvp.utils.PresenterSaverFragment;

public abstract class MvpFragment<P extends MvpPresenter<M>, M> extends Fragment implements MvpView<M> {

    public static final String MODEL = "model";

    protected P presenter;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        M restoredModel = null;
        if (state != null) {
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (restoredModel == null && getArguments() != null) {
            restoredModel = Parcels.unwrap(getArguments().getParcelable(MODEL));
        }

        presenter = PresenterSaverFragment.<P>load(getChildFragmentManager());
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.init(restoredModel);

        PresenterSaverFragment.save(getChildFragmentManager(), presenter);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
    }

    @Override public void onStart() {
        super.onStart();
        presenter.subscribe(this);
    }

    @Override public void onStop() {
        presenter.pause();
        super.onStop();
    }

    protected abstract P createPresenter();
}
