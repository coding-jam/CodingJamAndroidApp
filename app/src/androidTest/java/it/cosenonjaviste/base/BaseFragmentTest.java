package it.cosenonjaviste.base;

import android.content.Intent;

import it.cosenonjaviste.lib.mvp.BundlePresenterArgs;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.utils.SingleFragmentActivity;
import rx.functions.Action2;

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

    protected <P> Intent createIntent(Intent intent, Action2<PresenterArgs, P> action, P param) {
        BundlePresenterArgs args = new BundlePresenterArgs();
        action.call(args, param);
        return intent.putExtras(args.getBundle());
    }
}