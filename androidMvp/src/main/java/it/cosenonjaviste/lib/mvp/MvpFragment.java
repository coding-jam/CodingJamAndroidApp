package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class MvpFragment<P extends MvpPresenter<?, ?>> extends Fragment {

    protected P presenter;

    private ListenersRetainedFragment retainedFragment;

    protected abstract P createPresenter();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        retainedFragment = ListenersRetainedFragment.getOrCreate(getActivity(), state, getArguments(), getClass().getName(), this::createPresenter);
        presenter = (P) retainedFragment.getListener();
    }

    @Override public void onResume() {
        super.onResume();
        retainedFragment.resume(this);
    }

    @Override public void onPause() {
        super.onPause();
        retainedFragment.pause();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        retainedFragment.destroy();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        retainedFragment.saveState(outState);
    }
}
