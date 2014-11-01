package it.cosenonjaviste.mvp.base;

public class MvpConfig<M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> {

    private Class<? extends V> viewClass;

    private P presenter;

    public MvpConfig(Class<? extends V> viewClass, P presenter) {
        this.viewClass = viewClass;
        this.presenter = presenter;
    }

    public Class<? extends V> createView() {
        return viewClass;
    }

    protected P createPresenter() {
        return presenter;
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
