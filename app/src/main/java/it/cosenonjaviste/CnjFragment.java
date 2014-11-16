package it.cosenonjaviste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import it.cosenonjaviste.lib.mvp.BundlePresenterArgs;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.ConfigManager;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.utils.SingleFragmentActivity;

public abstract class CnjFragment<P extends RxMvpPresenter<M>, M> extends MvpFragment<P, M> {

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

    @Override public void open(Class<? extends MvpView<?>> viewClass, PresenterArgs args) {
        Class<MvpView<?>> fragmentClass = ConfigManager.singleton().get(viewClass);
        Intent intent = SingleFragmentActivity.createIntent(getActivity(), fragmentClass);
        if (args != null) {
            Bundle bundle = ((BundlePresenterArgs) args).getBundle();
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
    }

    public static <T> T createView(Context context, Class<? extends MvpView<?>> viewClass, PresenterArgs args) {
        Class<? extends MvpView<?>> fragmentClass = ConfigManager.singleton().get(viewClass);
        Fragment fragment = Fragment.instantiate(context, fragmentClass.getName());
        Bundle bundle = createArgs(args);
        fragment.setArguments(bundle);
        return (T) fragment;
    }

    private static Bundle createArgs(PresenterArgs args) {
        if (args != null) {
            return ((BundlePresenterArgs) args).getBundle();
        } else {
            return new Bundle();
        }
    }
}
