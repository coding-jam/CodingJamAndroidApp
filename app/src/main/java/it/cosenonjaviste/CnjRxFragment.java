package it.cosenonjaviste;

import android.content.Intent;
import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public abstract class CnjRxFragment<M> extends RxMvpFragment<M> {

    private RxMvpPresenter<M> presenter;

    @Override public final RxMvpPresenter<M> getPresenter() {
        return presenter;
    }

    @Override public void onCreate(Bundle state) {
        presenter = injectAndCreatePresenter();
        super.onCreate(state);
    }

    protected abstract RxMvpPresenter<M> injectAndCreatePresenter();

    @Override public <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model) {
        Intent intent = SingleFragmentActivity.createIntent(getActivity(), viewClass);
        intent.putExtra(MODEL, Parcels.wrap(model));
        getActivity().startActivity(intent);
    }
}
