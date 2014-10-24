package it.cosenonjaviste;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.post.PostDetailFragment;
import rx.functions.Action1;

@Singleton
public class CnjNavigator implements Navigator {

    @Inject
    public CnjNavigator() {
    }

    @Override public void show(ContextBinder contextBinder, Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction) {
        contextBinder.startNewActivity(PostDetailFragment.class.getName(), argsAction);
    }

    @Override public <T> T createFragment(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction) {
        return null;
    }

    @Override public void open(String url) {

    }
}
