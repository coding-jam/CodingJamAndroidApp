package it.cosenonjaviste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import it.cosenonjaviste.lib.mvp.Dagger2MvpFragment;
import it.cosenonjaviste.lib.mvp.MvpPresenter;
import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public abstract class Dagger2CnjFragment<P extends MvpPresenter<M>, M> extends Dagger2MvpFragment<P, M> {

    protected ApplicationComponent getComponent() {
        return ((CoseNonJavisteApp) getActivity().getApplication()).getComponent();
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

    @Override public <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model) {
        Intent intent = SingleFragmentActivity.createIntent(getActivity(), viewClass);
        intent.putExtra(MODEL, Parcels.wrap(model));
        getActivity().startActivity(intent);
    }

    public static <T, M> T createView(@NonNull Context context, @NonNull Class<? extends MvpView<M>> viewClass, @NonNull M model) {
        Fragment fragment = Fragment.instantiate(context, viewClass.getName());
        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL, Parcels.wrap(model));
        fragment.setArguments(bundle);
        return (T) fragment;
    }
}
