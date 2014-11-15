package it.cosenonjaviste.mvp;

import org.junit.Before;

import javax.inject.Inject;

import it.cosenonjaviste.CnjPresenterConfig;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.utils.ComponentBuilder;

public abstract class PresenterTest<V extends RxMvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    protected TestContextBinder contextBinder;

    private Class<V> viewClass;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    public PresenterTest(Class<V> viewClass) {
        this.viewClass = viewClass;
    }

    @Before
    public void setup() {
        initAfterInject();

        ConfigManager configManager = cnjPresenterConfig.init();
        contextBinder = new TestContextBinder(configManager);

        view = contextBinder.createView(viewClass, getArgs());
        presenter = contextBinder.getLastPresenter();
    }

    public <T> T createComponent(Class<T> c) {
        return ComponentBuilder.build(c);
    }

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    protected final Class<V> getConfig() {
        return null;
    }

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
