package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ListenersRetainedFragment extends Fragment {

    public static final String TAG = ListenersRetainedFragment.class.getName();

    private MvpPresenter<?, ?> listener;

    public static ListenersRetainedFragment getOrCreate(FragmentActivity activity, final Bundle state, final Bundle args, String fragmentTag, Factory factory) {
        ListenersRetainedFragment retainedFragment = getOrCreateFragment(activity.getSupportFragmentManager(),
                TAG + fragmentTag);
        MvpPresenter<?, ?> presenter = retainedFragment.getListener();
        if (presenter == null) {
            presenter = factory.create();
            retainedFragment.setListener(presenter);
        }
        ObjectLoader loader = new ObjectLoader() {
            @Override public Object load(String key) {
                Object value = null;
                if (state != null) {
                    value = state.getParcelable(key);
                }
                if (value == null && args != null) {
                    value = args.getParcelable(key);
                }
                return value;
            }
        };
        retainedFragment.getListener().loadState(loader);
        return retainedFragment;
    }

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

    public void resume(MvpFragment mvpFragment) {
        ((MvpPresenter) getListener()).resume(mvpFragment);
    }

    public void pause() {
        getListener().pause();
    }

    public void destroy() {
        getListener().detachView();
    }

    public void saveState(final Bundle outState) {
        ObjectSaver saver = new ObjectSaver() {
            @Override public void save(String key, Object value) {
                outState.putParcelable(key, (Parcelable) value);
            }
        };
        getListener().saveState(saver);
    }

    public interface Factory {
        MvpPresenter<?, ?> create();
    }
}