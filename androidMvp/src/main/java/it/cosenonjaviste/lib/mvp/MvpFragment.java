package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.MvpPresenter;
import it.cosenonjaviste.mvp.base.MvpView;

public abstract class MvpFragment<P extends MvpPresenter<M>, M> extends Fragment implements MvpView<M> {

    private static final String PRESENTER_ID = "presenterId";
    private static final String MODEL = "model";

    protected P presenter;

    private boolean newModelCreated = true;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        long presenterId = 0;
        M restoredModel = null;
        if (state != null) {
            presenterId = state.getLong(PRESENTER_ID, 0);
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
            newModelCreated = false;
        }

        presenter = PresenterSaverFragment.<P>load(getFragmentManager(), presenterId);
        presenter = ConfigManager.singleton().createAndInitPresenter(getViewClass(), restoredModel, presenter, new BundlePresenterArgs(getArguments()));

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
        if (newModelCreated) {
            loadOnFirstStart();
            newModelCreated = false;
        }
    }

    protected void loadOnFirstStart() {
    }

    @Override public void onStop() {
        presenter.pause();
        super.onStop();
    }

    protected abstract Class<? extends MvpView<M>> getViewClass();
}
