package it.cosenonjaviste.mvp;

import org.junit.Before;

import it.cosenonjaviste.MvpTestComponent;
import it.cosenonjaviste.TestContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.utils.ComponentBuilder;

public abstract class PresenterTest<V extends RxMvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    protected TestContextBinder contextBinder;

    @Before
    public void setup() {
//        component.inject(this);

        contextBinder = new TestContextBinder();

        initAfterInject();

        view = contextBinder.createView(getConfig(), getArgs());
        presenter = contextBinder.getLastPresenter();
    }

    public <T> T createComponent(Class<T> c) {
        MvpTestComponent mvpComponent = ComponentBuilder.build(MvpTestComponent.class);
        return ComponentBuilder.build(c, mvpComponent);
    }

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    protected abstract MvpConfig<V> getConfig();

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
