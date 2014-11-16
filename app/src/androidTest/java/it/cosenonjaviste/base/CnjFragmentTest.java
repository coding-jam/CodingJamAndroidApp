package it.cosenonjaviste.base;

import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.base.MvpView;

public abstract class CnjFragmentTest extends BaseFragmentTest {
    public CnjFragmentTest(Class<? extends MvpView<?>> viewClass) {
        super(viewClass, true);
    }

    @Override protected final Object[] getTestModules() {
        return new Object[]{new MvpTestModule(true), new MvpEspressoTestModule(), getTestModule()};
    }

    protected abstract Object getTestModule();
}
