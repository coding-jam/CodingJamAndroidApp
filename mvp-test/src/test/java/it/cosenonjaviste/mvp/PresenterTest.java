package it.cosenonjaviste.mvp;

import org.junit.Before;

import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;

public abstract class PresenterTest<V extends RxMvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    protected TestContextBinder contextBinder;

    @Before
    public void setup() {
        contextBinder = new TestContextBinder(this, new MvpTestModule(), getTestModule());

        initAfterInject();

        view = contextBinder.createView(getConfig(), getArgs());
        presenter = contextBinder.getLastPresenter();
    }

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    protected abstract MvpConfig<V> getConfig();

    protected abstract Object getTestModule();

    public <V1> V1 getLastView() {
        return contextBinder.getLastView();
    }

    public <M> M getLastModel() {
        return contextBinder.getLastModel();
    }

    public <P1> P1 getLastPresenter() {
        return contextBinder.getLastPresenter();
    }
}
