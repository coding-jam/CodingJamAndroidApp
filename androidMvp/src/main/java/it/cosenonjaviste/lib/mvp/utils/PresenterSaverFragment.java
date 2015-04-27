package it.cosenonjaviste.lib.mvp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import it.cosenonjaviste.lib.mvp.MvpPresenter;

public class PresenterSaverFragment extends Fragment {

    private static final String TAG = PresenterSaverFragment.class.getName();

    private MvpPresenter<?> presenter;

    public PresenterSaverFragment() {
        setRetainInstance(true);
    }

    public static void save(FragmentManager fragmentManager, MvpPresenter<?> presenter) {
        PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
        fragment.presenter = presenter;
    }

    private static PresenterSaverFragment getPresenterSaverFragment(FragmentManager fragmentManager) {
        PresenterSaverFragment fragment = (PresenterSaverFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PresenterSaverFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    public static <P extends MvpPresenter<?>> P load(FragmentManager fragmentManager) {
        PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
        return (P) fragment.presenter;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}
