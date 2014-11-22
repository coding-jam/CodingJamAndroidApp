package it.cosenonjaviste.mvp;

import org.junit.Before;
import org.mockito.Mockito;

import javax.inject.Inject;

import dagger.ObjectGraph;
import it.cosenonjaviste.CnjPresenterConfig;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.args.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public abstract class PresenterTest<V extends MvpView<?>, P extends RxMvpPresenter<?>> {

    protected V view;

    protected P presenter;

    private Class<V> viewClass;

    private MvpView<?> lastView;

    private PresenterArgs lastArgs;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    public PresenterTest(Class<V> viewClass) {
        this.viewClass = viewClass;
    }

    @Before
    public void setup() {
        ObjectGraph.create(new MvpJUnitTestModule(), getTestModule()).inject(this);
        initAfterInject();

        ConfigManager configManager = cnjPresenterConfig.init();

        view = mockView(viewClass);

        presenter = configManager.createAndInitPresenter(viewClass, getArgs());

        presenter.subscribe((MvpView) view);
    }

    private <V extends MvpView<?>> V mockView(Class<V> viewClass) {
        MvpView<?> view = Mockito.mock(viewClass);
        doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            Class<? extends MvpView<?>> newViewClass = (Class<? extends MvpView<?>>) arguments[0];
            lastView = mockView(newViewClass);
            lastArgs = (PresenterArgs) arguments[1];
            return null;
        }).when(view).open(any(), any());

        doAnswer(invocation -> new MapPresenterArgs()).when(view).createArgs();
        return (V) view;
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

    public V getView() {
        return view;
    }
}
