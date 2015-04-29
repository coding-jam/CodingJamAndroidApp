package it.cosenonjaviste.androidtest.base;

import android.content.Intent;

import org.parceler.Parcels;

import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public class FragmentRule extends ActivityRule<SingleFragmentActivity> {

    private final Class<?> viewClass;

    private Object model;

    public static <M> FragmentRule create(Class<?> viewClass, M model) {
        return new FragmentRule(viewClass, model);
    }

    private FragmentRule(Class<?> viewClass, Object model) {
        super(SingleFragmentActivity.class);
        this.viewClass = viewClass;
        this.model = model;
    }

    @Override protected Intent getLaunchIntent(String targetPackage, Class<SingleFragmentActivity> activityClass) {
        Intent intent = SingleFragmentActivity.populateIntent(super.getLaunchIntent(targetPackage, activityClass), viewClass);
        intent.putExtra(RxMvpFragment.MODEL, Parcels.wrap(model));
        return intent;
    }
}
