package it.cosenonjaviste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.lib.mvp.SingleFragmentActivity;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public abstract class CnjFragment<P extends RxMvpPresenter<M>, M> extends RxMvpFragment<P, M> {

    @Override public void onCreate(Bundle savedInstanceState) {
        ObjectGraphHolder.inject((DaggerApplication) getActivity().getApplication(), this);
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        ButterKnife.inject(this, view);
    }

    protected abstract int getLayoutId();

    @Override protected P createPresenter() {
        String string = getArguments().getString(SingleFragmentActivity.CONFIG_CLASS);
        MvpConfig<?, ?, ?> config = contextBinder.createConfig(string);
        return (P) config.createPresenter();
    }
}
