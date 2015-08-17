package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ListenersRetainedFragment extends Fragment {

    public static final String TAG = ListenersRetainedFragment.class.getName();

    private MvpPresenter<?, ?> listener;

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
        if (listener != null) {
            listener.destroy();
        }
    }

    public MvpPresenter<?, ?> getListener() {
        return listener;
    }

    public void setListener(MvpPresenter<?, ?> listener) {
        this.listener = listener;
    }
}