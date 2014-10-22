package it.cosenonjaviste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import it.cosenonjaviste.base.DaggerApplication;
import it.cosenonjaviste.base.ObjectGraphHolder;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
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
}
