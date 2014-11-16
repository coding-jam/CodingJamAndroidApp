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

    private Object lastModel;

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
            RxMvpPresenter presenter1 = configManager.createAndInitPresenter(newViewClass, (PresenterArgs) arguments[1]);
            lastModel = presenter1.getModel();
            return null;
        }).when(view).open(any(), any());
    }

    protected abstract Object getTestModule();

    protected PresenterArgs getArgs() {
        return null;
    }

    protected void initAfterInject() {
    }

    public <M> M getLastModel() {
        return (M) lastModel;
    }
}
