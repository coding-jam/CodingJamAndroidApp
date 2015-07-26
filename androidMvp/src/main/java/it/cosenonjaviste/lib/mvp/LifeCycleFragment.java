package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import java.util.Collection;

import rx.functions.Func0;

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

    protected <P extends LifeCycleListener> P getOrCreate(Func0<P> factory) {
        P presenter = (P) getListener("presenter");
        if (presenter == null) {
            presenter = factory.call();
            addListener("presenter", presenter);
        }
        return presenter;
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        retainedFragment = ListenersRetainedFragment.getOrCreateFragment(getChildFragmentManager());
        init();
        LifeCycleListener.ObjectLoader loader = new LifeCycleListener.ObjectLoader() {
            @Override public Object load(String key) {
                Object value = null;
                if (state != null) {
                    value = Parcels.unwrap(state.getParcelable(key));
                }
                if (value == null && getArguments() != null) {
                    value = Parcels.unwrap(getArguments().getParcelable(key));
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
                outState.putParcelable(key, Parcels.wrap(value));
            }
        };
        Collection<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener listener : listeners) {
            listener.saveState(saver);
        }
    }
}