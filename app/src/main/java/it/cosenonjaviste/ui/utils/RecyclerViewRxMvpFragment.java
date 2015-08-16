package it.cosenonjaviste.ui.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.RecyclerBinding;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;

public abstract class RecyclerViewRxMvpFragment<P extends RxMvpListPresenterAdapter<T, ?, ?>, T> extends MvpFragment<P> {

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerBinding binding = RecyclerBinding.bind(inflater.inflate(R.layout.recycler, null, false));

        binding.list.setAdapter(new BindableAdapter<>(presenter.getModel().getItems(), v -> createViewHolder(inflater, v)));
        binding.list.setLayoutManager(createLayoutManager());

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.cnj_border, R.color.cnj_selection);

        binding.setPresenter(presenter);

        initBinding(binding);

        return binding.getRoot();
    }

    protected void initBinding(RecyclerBinding binding) {

    }

    @NonNull protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), 2);
    }

    @NonNull protected abstract BindableViewHolder<T> createViewHolder(LayoutInflater inflater, ViewGroup v);
}
