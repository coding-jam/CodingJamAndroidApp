package it.cosenonjaviste.base;

import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.base.RxMvpView;

public abstract class CnjFragmentTest extends BaseFragmentTest {
    public CnjFragmentTest(Class<? extends RxMvpView<?>> viewClass) {
        super(viewClass, true);
    }

    @Override protected final Object[] getTestModules() {
        return new Object[]{new MvpTestModule(true), getTestModule()};
    }

    protected abstract Object getTestModule();
}
