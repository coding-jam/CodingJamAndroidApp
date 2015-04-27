package it.cosenonjaviste;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import rx.functions.Func0;

public class ObjectsMapRetainedFragment extends Fragment {

    private static final String TAG = ObjectsMapRetainedFragment.class.getName();

    private Object object;

    public ObjectsMapRetainedFragment() {
        setRetainInstance(true);
    }

    public static void save(FragmentManager fragmentManager, Object obj) {
        ObjectsMapRetainedFragment fragment = getSaverFragment(fragmentManager);
        fragment.object = obj;
    }

    private static ObjectsMapRetainedFragment getSaverFragment(FragmentManager fragmentManager) {
        ObjectsMapRetainedFragment fragment = (ObjectsMapRetainedFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new ObjectsMapRetainedFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    public static <P> P load(FragmentManager fragmentManager) {
        ObjectsMapRetainedFragment fragment = getSaverFragment(fragmentManager);
        return (P) fragment.object;
    }

    public static <C> C getOrCreate(FragmentManager fragmentManager, Func0<C> componentFactory) {
        C component = ObjectsMapRetainedFragment.load(fragmentManager);
        if (component == null) {
            component = componentFactory.call();
            ObjectsMapRetainedFragment.save(fragmentManager, component);
        }
        return component;
    }

    @Override public void onDestroy() {
        super.onDestroy();
//        for (MvpPresenter<?> presenter : object.values()) {
//            presenter.destroy();
//        }
    }
}
