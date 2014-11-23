package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.MvpPresenter;
import it.cosenonjaviste.mvp.base.MvpView;

public abstract class MvpFragment<P extends MvpPresenter<M>, M> extends Fragment implements MvpView<M> {

    private static final String PRESENTER_ID = "presenterId";
    public static final String MODEL = "model";

    protected P presenter;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        long presenterId = 0;
        M restoredModel = null;
        if (state != null) {
            presenterId = state.getLong(PRESENTER_ID, 0);
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (restoredModel == null && getArguments() != null) {
            restoredModel = Parcels.unwrap(getArguments().getParcelable(MODEL));
        }

        presenter = PresenterSaverFragment.<P>load(getFragmentManager(), presenterId);
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.init(restoredModel);

        PresenterSaverFragment.save(getFragmentManager(), presenter);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
        outState.putLong(PRESENTER_ID, presenter.getId());
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
