package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.parceler.Parcels;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.utils.ObjectsMapRetainedFragment;
import rx.functions.Func0;

public abstract class RxMvpFragment extends Fragment {

    @Inject LifeCycle lifeCycle;

    @Override public final void onCreate(Bundle state) {
        super.onCreate(state);
        init(state);
        lifeCycle.loadState(key -> {
            Object restoredModel = null;
            if (state != null) {
                restoredModel = Parcels.unwrap(state.getParcelable(key));
            }
            if (restoredModel == null && getArguments() != null) {
                restoredModel = Parcels.unwrap(getArguments().getParcelable(key));
            }
            return restoredModel;
        });
    }

    protected abstract void init(Bundle state);

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifeCycle.saveState((key, obj) -> outState.putParcelable(key, Parcels.wrap(obj)));
    }

    @Override public void onResume() {
        super.onResume();
        lifeCycle.emit(this, LifeCycle.EventType.RESUME);
    }

    @Override public void onPause() {
        super.onPause();
        lifeCycle.emit(this, LifeCycle.EventType.PAUSE);
    }

    protected <T> T createComponent(Func0<T> componentFactory) {
        return ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                componentFactory
        );
    }
}
