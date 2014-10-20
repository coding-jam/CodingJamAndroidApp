package it.cosenonjaviste;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import it.cosenonjaviste.base.DaggerApplication;
import it.cosenonjaviste.base.ObjectGraphHolder;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import rx.subscriptions.CompositeSubscription;

public abstract class CnjFragment<P extends RxMvpPresenter<M>, M> extends RxMvpFragment<P, M> {

    protected CompositeSubscription subscriptions = new CompositeSubscription();

    @Override public void onCreate(Bundle savedInstanceState) {
        ObjectGraphHolder.inject((DaggerApplication) getActivity().getApplication(), this);
        super.onCreate(savedInstanceState);
    }

    @Override public void onStop() {
        super.onStop();
        subscriptions.unsubscribe();
        subscriptions = new CompositeSubscription();
    }

    @Override protected void initView(View view) {
        super.initView(view);
        ButterKnife.inject(this, view);
    }

    @Override protected Navigator getNavigator() {
        return null;
    }
}
