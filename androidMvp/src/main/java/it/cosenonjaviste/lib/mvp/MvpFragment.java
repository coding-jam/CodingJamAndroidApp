package it.cosenonjaviste.lib.mvp;

public abstract class MvpFragment<P extends LifeCycleListener<?>> extends LifeCycleFragment {

    protected P presenter;

    @Override protected void init() {
        presenter = getOrCreate("presenter", this::createPresenter);
    }

    protected abstract P createPresenter();
}
