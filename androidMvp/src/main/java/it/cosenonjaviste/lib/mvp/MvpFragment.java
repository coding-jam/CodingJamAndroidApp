package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public abstract class MvpFragment<P extends MvpPresenter<?, ?>> extends Fragment {

    protected P presenter;

    protected void init() {
        P listener = (P) getListener();
        if (listener == null) {
            listener = createPresenter();
            setListener(listener);
        }
        presenter = listener;
    }

    protected abstract P createPresenter();

    private ListenersRetainedFragment retainedFragment;

    public MvpPresenter<?, ?> getListener() {
        return retainedFragment.getListener();
    }

    public void setListener(MvpPresenter<?, ?> listener) {
        retainedFragment.setListener(listener);
    }

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
        MvpPresenter<?, ?> listener = retainedFragment.getListener();
        if (listener != null) {
            listener.loadState(loader);
        }
    }

    @Override public void onResume() {
        super.onResume();
        MvpPresenter listener = retainedFragment.getListener();
        if (listener != null) {
            listener.resume(this);
        }
    }

    @Override public void onPause() {
        super.onPause();
        MvpPresenter<?, ?> listener = retainedFragment.getListener();
        if (listener != null) {
            listener.pause();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        MvpPresenter<?, ?> listener = retainedFragment.getListener();
        if (listener != null) {
            listener.detachView();
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MvpPresenter<?, ?> listener = retainedFragment.getListener();
        if (listener != null) {
            ObjectSaver saver = new ObjectSaver() {
                @Override public void save(String key, Object value) {
                    outState.putParcelable(key, (Parcelable) value);
                }
            };
            listener.saveState(saver);
        }
    }
}
