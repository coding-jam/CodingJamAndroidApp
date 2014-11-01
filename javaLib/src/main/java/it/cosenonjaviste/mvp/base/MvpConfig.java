package it.cosenonjaviste.mvp.base;

import rx.functions.Func0;

public class MvpConfig<M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> {

    private Class<? extends V> viewClass;

    private Func0<P> presenterFactory;

    public static <M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> MvpConfig<M, V, P> create(Class<? extends V> viewClass, Func0<P> presenterFactory) {
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

    public P createAndInitPresenter(ContextBinder contextBinder, M restoredModel, P restoredpresenter, PresenterArgs args) {
        P presenter;
        if (restoredpresenter == null) {
            presenter = createPresenter();
        } else {
            presenter = restoredpresenter;
        }
        M model;
        if (restoredModel != null) {
            model = restoredModel;
        } else {
            model = presenter.createModel(args != null ? args : PresenterArgs.EMPTY);
        }
        presenter.init(contextBinder, model);
        return presenter;
    }

}
