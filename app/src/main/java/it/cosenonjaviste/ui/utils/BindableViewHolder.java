package it.cosenonjaviste.ui.utils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import rx.functions.Action2;

public class BindableViewHolder<B extends ViewDataBinding, T> extends RecyclerView.ViewHolder {

    protected final B binding;

    private final Action2<B, T> binder;

    protected T item;

    public BindableViewHolder(B binding, Action2<B, T> binder) {
        super(binding.getRoot());
        this.binding = binding;
        this.binder = binder;
    }

    public void bind(T item) {
        this.item = item;
        binder.call(binding, item);
    }

    public T getItem() {
        return item;
    }
}
