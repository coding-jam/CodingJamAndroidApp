package it.cosenonjaviste.mvp;

import org.junit.Before;
import org.mockito.Mockito;

import javax.inject.Inject;

import it.cosenonjaviste.CnjPresenterConfig;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;
import it.cosenonjaviste.utils.ComponentBuilder;

import static org.mockito.Matchers.any;

public abstract class PresenterTest<V extends RxMvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    private Class<V> viewClass;

    private Object lastModel;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    @Inject protected PresenterArgsFactory presenterArgsFactory;

    public PresenterTest(Class<V> viewClass) {
        this.viewClass = viewClass;
    }

    @Before
    public void setup() {
        initAfterInject();

        ConfigManager configManager = cnjPresenterConfig.init();

        presenter = configManager.createAndInitPresenter(viewClass, getArgs());

        view = Mockito.mock(viewClass);

        presenter.subscribe((RxMvpView) view);

        Mockito.doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            Class<? extends RxMvpView<?>> newViewClass = (Class<? extends RxMvpView<?>>) arguments[0];
            RxMvpPresenter presenter1 = configManager.createAndInitPresenter(newViewClass, (PresenterArgs) arguments[1]);
            lastModel = presenter1.getModel();
            return null;
        }).when(view).open(any(), any());
    }

    public <T> T createComponent(Class<T> c) {
        return ComponentBuilder.build(c);
    }

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    public <M> M getLastModel() {
        return (M) lastModel;
    }
}
