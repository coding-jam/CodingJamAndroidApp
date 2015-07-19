package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ListenersRetainedFragment extends Fragment {

    private static final String TAG = ListenersRetainedFragment.class.getName();

    private List<LifeCycleListener<Object>> listeners = new ArrayList<>();

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
        for (LifeCycleListener<Object> listener : listeners) {
            listener.destroy();
        }
    }

    public void addListener(LifeCycleListener<Object> listener) {
        listeners.add(listener);
    }

    public List<LifeCycleListener<Object>> getListeners() {
        return listeners;
    }
}