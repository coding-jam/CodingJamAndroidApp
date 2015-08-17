package it.cosenonjaviste.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class MvpFragment<P extends MvpPresenter<?, ?>> extends Fragment {

    protected P presenter;

    protected abstract P createPresenter();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        presenter = ListenersRetainedFragment.getOrCreate(this, state, getFragmentTag(), this::createPresenter);
    }

    protected String getFragmentTag() {
        return getClass().getName();
    }

    @Override public void onResume() {
        super.onResume();
        ViewModelManager.resume(this, presenter);
    }

    @Override public void onPause() {
        super.onPause();
        ViewModelManager.pause(presenter);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        ViewModelManager.destroy(presenter);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ViewModelManager.saveState(outState, presenter);
    }
}
