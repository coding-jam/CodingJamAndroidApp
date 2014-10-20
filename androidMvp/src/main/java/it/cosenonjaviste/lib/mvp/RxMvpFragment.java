package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;

public abstract class RxMvpFragment<P extends RxMvpPresenter<M>, M> extends Fragment implements RxMvpView<M> {

    public static final String PRESENTER_ID = "presenterId";
    protected P presenter;

    private long presenterId;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter(savedInstanceState);

        BundleObjectSaver<M> objectSaver = new BundleObjectSaver<>(savedInstanceState, "model");
        BundlePresenterArgs args = new BundlePresenterArgs(getArguments());
        presenter.init(new ActivityContextBinder(getActivity()), objectSaver, args, getNavigator());
    }

    protected abstract Navigator getNavigator();

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInBundle(new BundleObjectSaver<>(outState, "model"));
        outState.putLong(PRESENTER_ID, presenterId);
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

    protected final P initPresenter(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            presenterId = savedInstanceState.getLong(PRESENTER_ID, 0);
            if (presenterId != 0) {
                presenter = PresenterSaverFragment.load(getFragmentManager(), presenterId);
            }
        }
        if (presenter == null) {
            presenter = createPresenter();
            presenterId = PresenterSaverFragment.save(getFragmentManager(), presenter);
        }
        return presenter;
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
