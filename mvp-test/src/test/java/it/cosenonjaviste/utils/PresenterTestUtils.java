package it.cosenonjaviste.utils;

import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;

public class PresenterTestUtils {
    public static <P extends RxMvpPresenter<M>, M> M init(ObjectGraph objectGraph, P presenter, PresenterArgs args, RxMvpView<M> view) {
        M model = presenter.init(new TestContextBinder(objectGraph), null, args);
        presenter.subscribe(view);
        return model;
    }
}
