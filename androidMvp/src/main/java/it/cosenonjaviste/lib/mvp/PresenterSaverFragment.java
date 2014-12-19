package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import it.cosenonjaviste.mvp.base.MvpPresenter;

public class PresenterSaverFragment extends Fragment {

    private static final String TAG = PresenterSaverFragment.class.getName();

    private Map<Long, MvpPresenter<?>> presenters = new HashMap<>();

    public PresenterSaverFragment() {
        setRetainInstance(true);
    }

    public static void save(FragmentManager fragmentManager, MvpPresenter<?> presenter) {
        PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
        fragment.presenters.put(presenter.getId(), presenter);
    }

    private static PresenterSaverFragment getPresenterSaverFragment(FragmentManager fragmentManager) {
        PresenterSaverFragment fragment = (PresenterSaverFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PresenterSaverFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    public static <P extends MvpPresenter<?>> P load(FragmentManager fragmentManager, long id) {
        if (id != 0) {
            PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
            return (P) fragment.presenters.get(id);
        } else {
            return null;
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        for (MvpPresenter<?> presenter : presenters.values()) {
            presenter.destroy();
        }
    }
}
