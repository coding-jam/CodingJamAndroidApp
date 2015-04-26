package it.cosenonjaviste;

import android.content.Intent;
import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.lib.mvp.Dagger2MvpFragment;
import it.cosenonjaviste.lib.mvp.MvpPresenter;
import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public abstract class Dagger2CnjFragment<M> extends Dagger2MvpFragment<M> {

    private MvpPresenter<M> presenter;

    @Override public final MvpPresenter<M> getPresenter() {
        return presenter;
    }

    protected ApplicationComponent getComponent() {
        return ((CoseNonJavisteApp) getActivity().getApplication()).getComponent();
    }

    @Override public void onCreate(Bundle state) {
        presenter = injectAndCreatePresenter();
        super.onCreate(state);
    }

    protected abstract MvpPresenter<M> injectAndCreatePresenter();

    @Override public <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model) {
        Intent intent = SingleFragmentActivity.createIntent(getActivity(), viewClass);
        intent.putExtra(MODEL, Parcels.wrap(model));
        getActivity().startActivity(intent);
    }
}
