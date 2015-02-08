package it.cosenonjaviste.androidtest.base;

import android.content.Intent;

import org.parceler.Parcels;

import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.mvp.base.MvpView;

public abstract class CnjFragmentTest<M> extends BaseFragmentTest {
    private final M model;

    public CnjFragmentTest(Class<? extends MvpView<M>> viewClass, M model) {
        super(viewClass, true);
        this.model = model;
    }

    @Override protected final Object[] getTestModules() {
        return new Object[]{getTestModule()};
    }

    @Override protected Intent createActivityIntent() {
        Intent activityIntent = super.createActivityIntent();
        activityIntent.putExtra(MvpFragment.MODEL, Parcels.wrap(model));
        return activityIntent;
    }

    protected abstract Object getTestModule();
}
