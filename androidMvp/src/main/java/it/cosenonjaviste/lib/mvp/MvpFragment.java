package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public abstract class MvpFragment<P extends MvpPresenter<?, ?>> extends Fragment {

    protected P presenter;

    private ListenersRetainedFragment retainedFragment;

    protected void init() {
        presenter = (P) retainedFragment.getListener();
        if (presenter == null) {
            presenter = createPresenter();
            retainedFragment.setListener(presenter);
        }
    }

    protected abstract P createPresenter();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        retainedFragment = ListenersRetainedFragment.getOrCreateFragment(getFragmentManager(),
                ListenersRetainedFragment.TAG + getClass().getName());
        init();
        ObjectLoader loader = new ObjectLoader() {
            @Override public Object load(String key) {
                Object value = null;
                if (state != null) {
                    value = state.getParcelable(key);
                }
                if (value == null && getArguments() != null) {
                    value = getArguments().getParcelable(key);
                }
                return value;
            }
        };
        retainedFragment.getListener().loadState(loader);
    }

    @Override public void onResume() {
        super.onResume();
        ((MvpPresenter) retainedFragment.getListener()).resume(this);
    }

    @Override public void onPause() {
        super.onPause();
        retainedFragment.getListener().pause();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        retainedFragment.getListener().detachView();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MvpPresenter<?, ?> listener = retainedFragment.getListener();
        ObjectSaver saver = new ObjectSaver() {
            @Override public void save(String key, Object value) {
                outState.putParcelable(key, (Parcelable) value);
            }
        };
        listener.saveState(saver);
    }
}
