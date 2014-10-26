package it.cosenonjaviste.mvp.base;

public abstract class MvpConfig<M, V extends RxMvpView<M>, P extends RxMvpPresenter<M>> {
    public abstract V createView();

    protected abstract P createPresenter();

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
            model = presenter.createModel(args);
        }
        presenter.init(contextBinder, model);
        return presenter;
    }

}
