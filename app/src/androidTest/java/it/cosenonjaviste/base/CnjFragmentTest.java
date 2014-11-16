package it.cosenonjaviste.base;

import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.mvp.base.RxMvpView;

public abstract class CnjFragmentTest extends BaseFragmentTest {
    public CnjFragmentTest(Class<? extends RxMvpView<?>> viewClass) {
        super(viewClass, true);
    }

    @Override protected final Object[] getTestModules() {
        Object testModule = getTestModule();
        MvpTestModule mvpTestModule = new MvpTestModule(true);
        if (testModule == null) {
            return new Object[]{mvpTestModule};
        } else {
            return new Object[]{mvpTestModule, testModule};
        }
    }

    protected abstract Object getTestModule();
}
