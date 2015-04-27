package it.cosenonjaviste.androidtest.base;

import android.content.Intent;

import org.parceler.Parcels;

import it.cosenonjaviste.Dagger2CnjFragment;
import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public class FragmentRule extends ActivityRule<SingleFragmentActivity> {

    private final Class<? extends MvpView<?>> viewClass;

    private Object model;

    public static <M> FragmentRule create(Class<? extends MvpView<M>> viewClass, M model) {
        return new FragmentRule(viewClass, model);
    }

    private FragmentRule(Class<? extends MvpView<?>> viewClass, Object model) {
        super(SingleFragmentActivity.class);
        this.viewClass = viewClass;
        this.model = model;
    }

    @Override protected Intent getLaunchIntent(String targetPackage, Class<SingleFragmentActivity> activityClass) {
        Intent intent = SingleFragmentActivity.populateIntent(super.getLaunchIntent(targetPackage, activityClass), viewClass);
        intent.putExtra(Dagger2CnjFragment.MODEL, Parcels.wrap(model));
        return intent;
    }
}
