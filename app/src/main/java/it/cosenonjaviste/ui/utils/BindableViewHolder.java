package it.cosenonjaviste.ui.utils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class BindableViewHolder<B extends ViewDataBinding, T> extends RecyclerView.ViewHolder {

    protected B binding;

    public BindableViewHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public abstract void bind(T item, int pos);
}
