package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ListenersRetainedFragment extends Fragment {

    public static final String TAG = ListenersRetainedFragment.class.getName();

    private LinkedHashMap<String, LifeCycleListener<Object>> listeners = new LinkedHashMap<>();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static ListenersRetainedFragment getOrCreateFragment(FragmentManager fragmentManager, String tag) {
        ListenersRetainedFragment fragment = (ListenersRetainedFragment) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new ListenersRetainedFragment();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
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