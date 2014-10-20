package it.cosenonjaviste.utils;

import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PresenterTestUtils {
    public static <P extends RxMvpPresenter<M>, M> M init(P presenter) {
        M model = presenter.init(new TestContextBinder(), new EmptyObjectSaver<>(), null, null);
        presenter.subscribe();
        return model;
    }
}
