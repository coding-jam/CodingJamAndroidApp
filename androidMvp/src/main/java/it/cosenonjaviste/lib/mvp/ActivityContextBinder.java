package it.cosenonjaviste.lib.mvp;

import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class ActivityContextBinder extends ContextBinder {

    public ActivityContextBinder(FragmentActivity activity) {
    }

    @Override public PresenterArgs createArgs() {
        return new BundlePresenterArgs();
    }
}
