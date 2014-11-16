package it.cosenonjaviste.mvp.base;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public class ViewIntent {
    private Class<? extends MvpView<?>> viewClass;

    private PresenterArgs args;

    public ViewIntent(Class<? extends MvpView<?>> viewClass, PresenterArgs args) {
        this.viewClass = viewClass;
        this.args = args;
    }

    public Class<? extends MvpView<?>> getViewClass() {
        return viewClass;
    }

    public PresenterArgs getArgs() {
        return args;
    }
}
