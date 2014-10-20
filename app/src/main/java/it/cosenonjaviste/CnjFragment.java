package it.cosenonjaviste;

import android.view.View;

import butterknife.ButterKnife;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public abstract class CnjFragment<P extends RxMvpPresenter<M>, M> extends RxMvpFragment<P, M> {
    @Override protected void initView(View view) {
        super.initView(view);
        ButterKnife.inject(this, view);
    }
}
