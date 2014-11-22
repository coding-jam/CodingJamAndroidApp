package it.cosenonjaviste.mvp;

import org.junit.Before;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.CnjPresenterConfig;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

public abstract class PresenterTest<V extends MvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    private Class<V> viewClass;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    private ViewMocker viewMocker = new ViewMocker();

    public PresenterTest(Class<V> viewClass) {
        this.viewClass = viewClass;
    }

    @Before
    public void setup() {
        ObjectGraph.create(new MvpJUnitTestModule(), getTestModule()).inject(this);
        initAfterInject();

        ConfigManager configManager = cnjPresenterConfig.init();

        view = viewMocker.mockView(viewClass);

        presenter = configManager.createAndInitPresenter(viewClass, getArgs());

        presenter.subscribe((MvpView) view);
    }

    protected abstract Object getTestModule();

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    public MvpView<?> getLastView() {
        return viewMocker.getLastView();
    }

    public PresenterArgs getLastArgs() {
        return viewMocker.getLastArgs();
    }

    public V getView() {
        return view;
    }
}
