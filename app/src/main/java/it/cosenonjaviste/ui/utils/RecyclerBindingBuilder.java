package it.cosenonjaviste.ui.utils;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.list.ListModel;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.databinding.RecyclerBinding;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.functions.Func3;

public class RecyclerBindingBuilder<T> {

    private final LayoutInflater inflater;

    private final RxListViewModel<? extends ListModel<T>, ?> viewModel;

    private RecyclerBinding binding;

    public RecyclerBindingBuilder(LayoutInflater inflater, @Nullable ViewGroup container, RxListViewModel<? extends ListModel<T>, ?> viewModel) {
        this.inflater = inflater;
        this.viewModel = viewModel;
        binding = RecyclerBinding.bind(inflater.inflate(R.layout.recycler, container, false));
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.cnj_border, R.color.cnj_selection);
        binding.setViewModel(viewModel);
    }

    public RecyclerBinding getBinding() {
        if (binding.list.getLayoutManager() == null) {
            linearLayoutManager();
        }
        return binding;
    }

    public View getRoot() {
        return getBinding().getRoot();
    }

    public RecyclerBindingBuilder<T> linearLayoutManager() {
        binding.list.setLayoutManager(new LinearLayoutManager(binding.list.getContext()));
        return this;
    }

    public RecyclerBindingBuilder<T> gridLayoutManager(int spanCount) {
        binding.list.setLayoutManager(new GridLayoutManager(binding.list.getContext(), spanCount));
        return this;
    }

    public RecyclerBindingBuilder<T> loadMoreListener(Runnable listener) {
        binding.list.addOnScrollListener(new EndlessRecyclerOnScrollListener(listener));
        return this;
    }

    private <B extends ViewDataBinding> RecyclerBindingBuilder<T> viewHolderWithCustomizer(
            Func3<LayoutInflater, ViewGroup, Boolean, B> inflateFunction,
            Action2<B, T> binder,
            Action1<BindableViewHolder<B, T>> customizer) {
        Func1<ViewGroup, BindableViewHolder<?, T>> factory = v -> {
            B binding = inflateFunction.call(inflater, v, false);
            BindableViewHolder<B, T> viewHolder = new BindableViewHolder<>(binding, binder);
            if (customizer != null) {
                customizer.call(viewHolder);
            }
            return viewHolder;
        };
        binding.list.setAdapter(new BindableAdapter<>(viewModel.getModel().getItems(), factory));
        return this;
    }

    public <B extends ViewDataBinding> RecyclerBindingBuilder<T> viewHolder(Func3<LayoutInflater, ViewGroup, Boolean, B> inflateFunction, Action2<B, T> binder) {
        return viewHolderWithCustomizer(inflateFunction, binder, null);
    }

    public <B extends ViewDataBinding> RecyclerBindingBuilder<T> viewHolder(
            Func3<LayoutInflater, ViewGroup, Boolean, B> inflateFunction,
            Action2<B, T> binder,
            Action1<BindableViewHolder<B, T>> clickListener) {
        return viewHolderWithCustomizer(inflateFunction, binder, vh -> vh.itemView.setOnClickListener(v -> clickListener.call(vh)));
    }
}
