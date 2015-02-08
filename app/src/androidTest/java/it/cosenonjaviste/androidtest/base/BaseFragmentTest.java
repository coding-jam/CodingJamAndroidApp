package it.cosenonjaviste.androidtest.base;

import android.content.Intent;

import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public abstract class BaseFragmentTest extends BaseActivityTest<SingleFragmentActivity> {
    private final Class<? extends MvpView<?>> viewClass;

    public BaseFragmentTest(Class<? extends MvpView<?>> viewClass) {
        this(viewClass, false);
    }

    public BaseFragmentTest(Class<? extends MvpView<?>> viewClass, boolean injectTest) {
        super(SingleFragmentActivity.class, injectTest);
        this.viewClass = viewClass;
    }

    @Override protected Intent createActivityIntent() {
        return SingleFragmentActivity.createIntent(viewClass);
    }
}