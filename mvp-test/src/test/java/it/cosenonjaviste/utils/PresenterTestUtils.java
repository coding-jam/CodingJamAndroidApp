package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PresenterTestUtils {
    public static <P extends RxMvpPresenter<M>, M> M init(P presenter, PresenterArgs args) {
        M model = presenter.init(new TestContextBinder(), null, args);
        presenter.subscribe(null);
        return model;
    }
}
