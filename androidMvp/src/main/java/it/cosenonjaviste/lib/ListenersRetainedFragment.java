package it.cosenonjaviste.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ListenersRetainedFragment<P extends MvpPresenter<?, ?>> extends Fragment {

    public static final String TAG = ListenersRetainedFragment.class.getName();

    private P listener;

    public static <P extends MvpPresenter<?, ?>> P getOrCreate(Fragment fragment, Bundle state, String fragmentTag, ViewModelManager.Factory<P> factory) {
        ListenersRetainedFragment<P> retainedFragment = getOrCreateFragment(fragment.getFragmentManager(), TAG + fragmentTag);
        retainedFragment.listener = ViewModelManager.init(retainedFragment.listener, state, fragment.getArguments(), factory);
        return retainedFragment.listener;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static <P extends MvpPresenter<?, ?>> ListenersRetainedFragment<P> getOrCreateFragment(FragmentManager fragmentManager, String tag) {
        ListenersRetainedFragment<P> fragment = (ListenersRetainedFragment<P>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new ListenersRetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        listener.destroy();
    }
}