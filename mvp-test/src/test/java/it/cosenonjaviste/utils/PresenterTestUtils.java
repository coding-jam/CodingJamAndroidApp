package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PresenterTestUtils {
    public static <P extends RxMvpPresenter<M>, M> M init(P presenter, PresenterArgs args, Navigator navigator) {
        M model = presenter.init(new TestContextBinder(), null, args, navigator);
        presenter.subscribe(null);
        return model;
    }
}
