package it.cosenonjaviste.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

import it.cosenonjaviste.lib.mvp.BundlePresenterArgs;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.utils.SingleFragmentActivity;
import rx.functions.Action2;

public class BaseFragmentTest extends BaseActivityTest<SingleFragmentActivity> {
    private final Class<? extends Fragment> fragmentClass;

    public BaseFragmentTest(Class<? extends Fragment> fragmentClass) {
        this(fragmentClass, false);
    }

    public BaseFragmentTest(Class<? extends Fragment> fragmentClass, boolean injectTest) {
        super(SingleFragmentActivity.class, injectTest);
        this.fragmentClass = fragmentClass;
    }

    @Override protected Intent createActivityIntent() {
        return SingleFragmentActivity.createIntent(fragmentClass);
    }

    protected <P> Intent createIntent(Intent intent, Action2<PresenterArgs, P> action, P param) {
        BundlePresenterArgs args = new BundlePresenterArgs();
        action.call(args, param);
        return intent.putExtras(args.getBundle());
    }
}