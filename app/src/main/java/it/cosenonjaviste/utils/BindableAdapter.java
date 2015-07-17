package it.cosenonjaviste.utils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class BindableAdapter<T> extends RecyclerView.Adapter<BindableViewHolder<T>> {

    private List<T> authors = new ArrayList<>();

    private Func1<ViewGroup, BindableViewHolder<T>> viewHolderFactory;

    public BindableAdapter(Func1<ViewGroup, BindableViewHolder<T>> viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    @Override public BindableViewHolder<T> onCreateViewHolder(ViewGroup viewGroup, int i) {
        return viewHolderFactory.call(viewGroup);
    }

    @Override public void onBindViewHolder(BindableViewHolder<T> authorViewHolder, int i) {
        authorViewHolder.bind(authors.get(i), i);
    }

    @Override public int getItemCount() {
        return authors == null ? 0 : authors.size();
    }

    public void reloadData(List<T> posts) {
        this.authors = posts;
        notifyDataSetChanged();
    }
}
