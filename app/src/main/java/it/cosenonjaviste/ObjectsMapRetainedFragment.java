package it.cosenonjaviste;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Func0;

public class ObjectsMapRetainedFragment extends Fragment {

    private static final String TAG = ObjectsMapRetainedFragment.class.getName();

    private Map<String, Object> map = new HashMap<>();

    public ObjectsMapRetainedFragment() {
        setRetainInstance(true);
    }

    public static void save(FragmentManager fragmentManager, String key, Object obj) {
        ObjectsMapRetainedFragment fragment = getSaverFragment(fragmentManager);
        fragment.map.put(key, obj);
    }

    private static ObjectsMapRetainedFragment getSaverFragment(FragmentManager fragmentManager) {
        ObjectsMapRetainedFragment fragment = (ObjectsMapRetainedFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new ObjectsMapRetainedFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    public static <P> P load(FragmentManager fragmentManager, String key) {
        if (key != null) {
            ObjectsMapRetainedFragment fragment = getSaverFragment(fragmentManager);
            return (P) fragment.map.get(key);
        } else {
            return null;
        }
    }

    public static <C> C getOrCreate(FragmentManager fragmentManager, String key, Func0<C> componentFactory) {
        C component = ObjectsMapRetainedFragment.load(fragmentManager, key);
        if (component == null) {
            component = componentFactory.call();
            ObjectsMapRetainedFragment.save(fragmentManager, key, component);
        }
        return component;
    }

    @Override public void onDestroy() {
        super.onDestroy();
//        for (MvpPresenter<?> presenter : map.values()) {
//            presenter.destroy();
//        }
    }
}
