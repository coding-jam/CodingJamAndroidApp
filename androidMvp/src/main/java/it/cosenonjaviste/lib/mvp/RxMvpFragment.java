package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public abstract class RxMvpFragment<P extends RxMvpPresenter<M>, M> extends Fragment {

    private static final String PRESENTER_ID = "presenterId";
    private static final String MODEL = "model";

    protected P presenter;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        long presenterId = 0;
        M restoredModel = null;
        if (state != null) {
            presenterId = state.getLong(RxMvpFragment.PRESENTER_ID, 0);
            restoredModel = Parcels.unwrap(state.getParcelable(MODEL));
        }

        presenter = PresenterSaverFragment.initPresenter(presenterId, getFragmentManager(), this::createPresenter);

        presenter.init(new ActivityContextBinder(getActivity()), restoredModel, new BundlePresenterArgs(getArguments()), getNavigator());
    }

    protected abstract Navigator getNavigator();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
        outState.putLong(PRESENTER_ID, presenter.getId());
    }

    @Override public void onStart() {
        super.onStart();
        presenter.subscribe(this::subscribeToModelUpdates);
    }

    protected abstract void subscribeToModelUpdates(Observable<ModelEvent<M>> modelUpdates, CompositeSubscription subscription);

    @Override public void onStop() {
        presenter.pause();
        super.onStop();
    }

    protected abstract P createPresenter();
}
