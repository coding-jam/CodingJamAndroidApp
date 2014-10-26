package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;

public abstract class RxMvpFragment<P extends RxMvpPresenter<M>, M> extends Fragment implements RxMvpView<M> {

    private static final String PRESENTER_ID = "presenterId";
    private static final String MODEL = "model";

    protected P presenter;

    protected ActivityContextBinder contextBinder;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        contextBinder = new ActivityContextBinder(getActivity());

        long presenterId = 0;
        M restoredModel = null;
        if (state != null) {
            presenterId = state.getLong(PRESENTER_ID, 0);
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }

        presenter = PresenterSaverFragment.initPresenter(presenterId, getFragmentManager(), this::createPresenter);

        presenter.init(contextBinder, restoredModel, new BundlePresenterArgs(getArguments()));
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

    protected abstract P createPresenter();}
