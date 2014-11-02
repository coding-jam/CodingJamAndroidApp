package it.cosenonjaviste.mvp.base;

import rx.functions.Func0;

public class MvpConfig<V extends RxMvpView<?>, P extends RxMvpPresenter<?>> {

    private Class<? extends V> viewClass;

    private Func0<P> presenterFactory;

    public static <M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> MvpConfig<V, P> create(Class<? extends V> viewClass, Func0<P> presenterFactory) {
        return new MvpConfig<>(viewClass, presenterFactory);
    }

    private MvpConfig(Class<? extends V> viewClass, Func0<P> presenterFactory) {
        this.viewClass = viewClass;
        this.presenterFactory = presenterFactory;
    }

    public Class<? extends V> createView() {
        return viewClass;
    }

    protected P createPresenter() {
        return presenterFactory.call();
    }

    public P createAndInitPresenter(ContextBinder contextBinder, PresenterArgs args) {
        return createAndInitPresenter(contextBinder, null, null, args);
    }

    public <M> P createAndInitPresenter(ContextBinder contextBinder, M restoredModel, P restoredPresenter, PresenterArgs args) {
        P presenter;
        if (restoredPresenter == null) {
            presenter = createPresenter();
        } else {
            presenter = restoredPresenter;
        }
        M model;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            model = (M) presenter.createModel(args != null ? args : PresenterArgs.EMPTY);
        }
        ((RxMvpPresenter) presenter).init(contextBinder, model);
        return presenter;
    }

}
