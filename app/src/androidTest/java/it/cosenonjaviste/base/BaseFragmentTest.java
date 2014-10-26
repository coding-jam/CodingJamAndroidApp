package it.cosenonjaviste.base;

import android.content.Intent;

import it.cosenonjaviste.lib.mvp.BundlePresenterArgs;
import it.cosenonjaviste.lib.mvp.SingleFragmentActivity;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import rx.functions.Action2;

public class BaseFragmentTest extends BaseActivityTest<SingleFragmentActivity> {
    private final Class<? extends MvpConfig<?, ?, ?>> configClass;

    public BaseFragmentTest(Class<? extends MvpConfig<?, ?, ?>> configClass) {
        this(configClass, false);
    }

    public BaseFragmentTest(Class<? extends MvpConfig<?, ?, ?>> configClass, boolean injectTest) {
        super(SingleFragmentActivity.class, injectTest);
        this.configClass = configClass;
    }

    @Override protected Intent createActivityIntent() {
        return SingleFragmentActivity.createIntent(configClass);
    }

    protected <P> Intent createIntent(Intent intent, Action2<PresenterArgs, P> action, P param) {
        BundlePresenterArgs args = new BundlePresenterArgs();
        action.call(args, param);
        return intent.putExtras(args.getBundle());
    }
}