package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;

public abstract class RxMvpFragment<P extends RxMvpPresenter<M>, M> extends Fragment {

    public static final String PRESENTER_ID = "presenterId";
    public static final String MODEL = "model";
    protected P presenter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = PresenterSaverFragment.initPresenter(savedInstanceState, getFragmentManager(), new PresenterSaverFragment.PresenterFactory<P>() {
            @Override public P create() {
                return createPresenter();
            }
        });

        M restoredModel = savedInstanceState != null ? Parcels.unwrap(savedInstanceState.getParcelable(MODEL)) : null;
        BundlePresenterArgs args = new BundlePresenterArgs(getArguments());
        presenter.init(new ActivityContextBinder(getActivity()), restoredModel, args, getNavigator());
    }

    protected abstract Navigator getNavigator();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
        outState.putLong(PRESENTER_ID, presenter.getId());
    }

    @Override public void onStart() {
        super.onStart();
        Observable<ModelEvent<M>> modelUpdates = presenter.getModelUpdates();
        subscribeToModelUpdates(modelUpdates);
        presenter.subscribe();
    }

    protected void subscribeToModelUpdates(Observable<ModelEvent<M>> modelUpdates) {
    }

    @Override public void onStop() {
        presenter.pause();
        super.onStop();
    }

    protected abstract P createPresenter();

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    protected void initView(View view) {

    }

    protected abstract int getLayoutId();
}
