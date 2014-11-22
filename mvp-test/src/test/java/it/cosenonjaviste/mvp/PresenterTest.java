package it.cosenonjaviste.mvp;

import org.junit.Before;
import org.mockito.Mockito;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.CnjPresenterConfig;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;

import static org.mockito.Matchers.any;

public abstract class PresenterTest<V extends MvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    private Class<V> viewClass;

    private MvpView<?> lastView;

    private PresenterArgs lastArgs;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    @Inject protected PresenterArgsFactory presenterArgsFactory;

    public PresenterTest(Class<V> viewClass) {
        this.viewClass = viewClass;
    }

    @Before
    public void setup() {
        ObjectGraph.create(new MvpJUnitTestModule(), getTestModule()).inject(this);
        initAfterInject();

        ConfigManager configManager = cnjPresenterConfig.init();

        presenter = configManager.createAndInitPresenter(viewClass, getArgs());

        view = Mockito.mock(viewClass);

        presenter.subscribe((MvpView) view);

        Mockito.doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            Class<? extends MvpView<?>> newViewClass = (Class<? extends MvpView<?>>) arguments[0];
            lastView = Mockito.mock(newViewClass);
            lastArgs = (PresenterArgs) arguments[1];
            return null;
        }).when(view).open(any(), any());
    }

    protected abstract Object getTestModule();

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    public MvpView<?> getLastView() {
        return lastView;
    }

    public PresenterArgs getLastArgs() {
        return lastArgs;
    }
}
