package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

public abstract class Dagger2MvpFragment<P extends MvpPresenter<M>, M> extends Fragment implements MvpView<M> {

    private static final String PRESENTER_ID = "presenterId";
    public static final String MODEL = "model";

//    protected P presenter;

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

//        presenter = PresenterSaverFragment.<P>load(getFragmentManager(), presenterId);
//        if (presenter == null) {
//            presenter = createPresenter();
//        }
        getPresenter().init(restoredModel);

//        PresenterSaverFragment.save(getFragmentManager(), presenter);
    }

    public abstract P getPresenter();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(getPresenter().getModel()));
        outState.putLong(PRESENTER_ID, getPresenter().getId());
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
