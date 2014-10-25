package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.RxMvp2Presenter;
import it.cosenonjaviste.mvp.base.RxMvpView;

public abstract class RxMvp2Fragment<M, V extends RxMvpView<M>, P extends RxMvp2Presenter<M, V>> extends Fragment implements RxMvpView<M> {

    private static final String PRESENTER_ID = "presenterId";
    private static final String MODEL = "model";

    protected P presenter;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        long presenterId = 0;
        M restoredModel = null;
        if (state != null) {
            presenterId = state.getLong(RxMvp2Fragment.PRESENTER_ID, 0);
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }

        presenter = PresenterSaverFragment.initPresenter(presenterId, getFragmentManager(), this::createPresenter);

        presenter.init(new ActivityContextBinder(getActivity()), restoredModel, new BundlePresenterArgs(getArguments()));
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
        outState.putLong(PRESENTER_ID, presenter.getId());
    }

    @Override public void onStart() {
        super.onStart();
        presenter.subscribe(getMvpView());
    }

    protected abstract V getMvpView();

    @Override public void onStop() {
        presenter.pause();
        super.onStop();
    }

    protected abstract P createPresenter();
}
