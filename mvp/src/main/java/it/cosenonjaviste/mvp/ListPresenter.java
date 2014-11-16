package it.cosenonjaviste.mvp;

import java.util.List;

import it.cosenonjaviste.mvp.base.MvpListView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import rx.Observable;

public abstract class ListPresenter<I> extends RxMvpPresenter<OptionalList<I>> {

    public ListPresenter(SchedulerManager schedulerManager, PresenterArgsFactory presenterArgsFactory) {
        super(schedulerManager, presenterArgsFactory);
    }

    @Override public MvpListView<I> getView() {
        return (MvpListView<I>) super.getView();
    }

    protected void subscribeListObservable(Observable<List<I>> observable) {
        subscribeListObservable(observable, false);
    }

    protected void subscribeListObservable(Observable<List<I>> observable, boolean appendData) {
        subscribePausable(observable,
                () -> getView().startLoading(),
                posts -> {
                    if (appendData) {
                        model.append(posts);
                    } else {
                        model.done(posts);
                    }
                    view.update(model);
                }, throwable -> {
                    model.error(throwable);
                    view.update(model);
                });
    }

    public OptionalList<I> createModel(PresenterArgs args) {
        return new OptionalList<>();
    }
}
