package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PresenterSaverFragment extends Fragment {

    private static final String TAG = PresenterSaverFragment.class.getName();

    private static AtomicLong sequence = new AtomicLong(1);

    private Map<Long, RxMvpPresenter<?>> presenters = new HashMap<>();

    public PresenterSaverFragment() {
        setRetainInstance(true);
    }

    public static long save(FragmentManager fragmentManager, RxMvpPresenter<?> presenter) {
        long id = sequence.getAndIncrement();
        PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
        fragment.presenters.put(id, presenter);
        return id;
    }

    private static PresenterSaverFragment getPresenterSaverFragment(FragmentManager fragmentManager) {
        PresenterSaverFragment fragment = (PresenterSaverFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new PresenterSaverFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    public static <P extends RxMvpPresenter<?>> P load(FragmentManager fragmentManager, long id) {
        PresenterSaverFragment fragment = getPresenterSaverFragment(fragmentManager);
        return (P) fragment.presenters.get(id);
    }

    protected static <P extends RxMvpPresenter<?>> P initPresenter(Bundle savedInstanceState, FragmentManager fragmentManager, PresenterFactory<P> presenterCreator) {
        P presenter = null;
        long presenterId = 0;
        if (savedInstanceState != null) {
            presenterId = savedInstanceState.getLong(RxMvpFragment.PRESENTER_ID, 0);
            if (presenterId != 0) {
                presenter = load(fragmentManager, presenterId);
            }
        }
        if (presenter == null) {
            presenter = presenterCreator.create();
            presenterId = save(fragmentManager, presenter);
        }
        presenter.setId(presenterId);
        return presenter;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        for (RxMvpPresenter<?> presenter : presenters.values()) {
            presenter.destroy();
        }
    }

    public interface PresenterFactory<P> {
        P create();
    }
}
