package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public abstract class LifeCycleFragment extends Fragment {
    private ListenersRetainedFragment retainedFragment;

    private List<InstanceStateListener> instanceStateListeners = new ArrayList<>();

    public void addListener(LifeCycleListener<?> listener) {
        retainedFragment.addListener((LifeCycleListener<Object>) listener);
    }

    public void addInstanceStateListener(InstanceStateListener listener) {
        instanceStateListeners.add(listener);
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        retainedFragment = ListenersRetainedFragment.getOrCreateFragment(getChildFragmentManager());
        init();
        InstanceStateListener.ObjectLoader loader = new InstanceStateListener.ObjectLoader() {
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
        for (InstanceStateListener listener : instanceStateListeners) {
            listener.loadState(loader);
        }
    }

    protected abstract void init();

    @Override public void onResume() {
        super.onResume();
        List<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.resume(this);
        }
    }

    @Override public void onPause() {
        super.onPause();
        List<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.pause();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        List<LifeCycleListener<Object>> listeners = retainedFragment.getListeners();
        for (LifeCycleListener<Object> listener : listeners) {
            listener.detachView();
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        InstanceStateListener.ObjectSaver saver = new InstanceStateListener.ObjectSaver() {
            @Override public void save(String key, Object value) {
                outState.putParcelable(key, Parcels.wrap(value));
            }
        };
        for (InstanceStateListener listener : instanceStateListeners) {
            listener.saveState(saver);
        }
    }
}