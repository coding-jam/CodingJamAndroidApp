package it.cosenonjaviste.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.lib.mvp.RxMvpListView;

public abstract class RecyclerViewRxMvpFragment<T> extends RxMvpFragment implements RxMvpListView<T> {
    protected BindableAdapter<T> adapter;

    @InjectView(R.id.recycler) SuperRecyclerView superRecycler;

    @InjectView(R.id.error_root) View errorLayout;

    public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        superRecycler.hideRecycler();
        superRecycler.hideProgress();
    }

    public void startLoading() {
        superRecycler.showProgress();
        errorLayout.setVisibility(View.GONE);
    }

    public void update(List<T> items) {
        superRecycler.showRecycler();
        adapter.reloadData(items);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_recycler, container, false);
        ButterKnife.inject(this, view);

        CircleTransform transformation = CircleTransform.createWithBorder(getActivity(), R.dimen.author_image_size_big, R.color.colorPrimary, R.dimen.author_image_border);
        adapter = new BindableAdapter<>(v -> createViewHolder(inflater, transformation, v));
        superRecycler.setAdapter(adapter);
        superRecycler.setLayoutManager(createGridLayoutManager());
        return view;
    }

    @NonNull protected RecyclerView.LayoutManager createGridLayoutManager() {
        return new GridLayoutManager(getActivity(), 2);
    }

    @NonNull protected abstract BindableViewHolder<T> createViewHolder(LayoutInflater inflater, CircleTransform transformation, ViewGroup v);
}
