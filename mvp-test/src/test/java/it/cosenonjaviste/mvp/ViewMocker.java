package it.cosenonjaviste.mvp;

import org.mockito.Mockito;

import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.args.MapPresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public class ViewMocker {
    private MvpView<?> lastView;

    private PresenterArgs lastArgs;

    public <V extends MvpView<?>> V mockView(Class<V> viewClass) {
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

    public MvpView<?> getLastView() {
        return lastView;
    }

    public PresenterArgs getLastArgs() {
        return lastArgs;
    }
}
