package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ListenersRetainedFragment extends Fragment {

    private static final String TAG = ListenersRetainedFragment.class.getName();

    private LinkedHashMap<String, LifeCycleListener<Object>> listeners = new LinkedHashMap<>();

    public ListenersRetainedFragment() {
        setRetainInstance(true);
    }

    public static ListenersRetainedFragment getOrCreateFragment(FragmentManager fragmentManager) {
        ListenersRetainedFragment fragment = (ListenersRetainedFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new ListenersRetainedFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        for (LifeCycleListener<Object> listener : listeners.values()) {
            listener.destroy();
        }
    }

    public void addListener(String key, LifeCycleListener<Object> listener) {
        listeners.put(key, listener);
    }

    public LifeCycleListener<Object> getListener(String key) {
        return listeners.get(key);
    }

    public Collection<LifeCycleListener<Object>> getListeners() {
        return listeners.values();
    }
}