package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.util.Collection;

import it.cosenonjaviste.lib.mvp.utils.ComponentFactory;

public abstract class LifeCycleFragment extends Fragment {
    private ListenersRetainedFragment retainedFragment;

    public void addListener(LifeCycleListener<?> listener) {
        String key = listener.getClass().getName();
        addListener(key, (LifeCycleListener<Object>) listener);
    }

    public void addListener(String key, LifeCycleListener<Object> listener) {
        retainedFragment.addListener(key, listener);
    }

    public LifeCycleListener<Object> getListener(String key) {
        return retainedFragment.getListener(key);
    }

    protected <P extends LifeCycleListener> P getOrCreate(String key, ComponentFactory<P> factory) {
        P listener = (P) getListener(key);
        if (listener == null) {
            listener = factory.call();
            addListener(key, listener);
        }
        return listener;
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        retainedFragment = ListenersRetainedFragment.getOrCreateFragment(getFragmentManager(),
                ListenersRetainedFragment.TAG + getClass().getName());
        init();
        LifeCycleListener.ObjectLoader loader = new LifeCycleListener.ObjectLoader() {
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
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener listener : listeners) {
            listener.loadState(loader);
        }
    }

    protected abstract void init();

    @Override public void onResume() {
        super.onResume();
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.resume(this);
        }
    }

    @Override public void onPause() {
        super.onPause();
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.pause();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.detachView();
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LifeCycleListener.ObjectSaver saver = new LifeCycleListener.ObjectSaver() {
            @Override public void save(String key, Object value) {
                outState.putParcelable(key, (Parcelable) value);
            }
        };
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener listener : listeners) {
            listener.saveState(saver);
        }
    }
}