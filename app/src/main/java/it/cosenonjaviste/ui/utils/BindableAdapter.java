package it.cosenonjaviste.ui.utils;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import rx.functions.Func1;

public class BindableAdapter<T> extends RecyclerView.Adapter<BindableViewHolder<?, T>> {

    private final ObservableList.OnListChangedCallback<ObservableList<T>> onListChangedCallback;
    private ObservableArrayList<T> items;

    private Func1<ViewGroup, BindableViewHolder<?, T>> viewHolderFactory;

    public BindableAdapter(ObservableArrayList<T> items, Func1<ViewGroup, BindableViewHolder<?, T>> viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
        this.items = items;
        //saved as field to avoid gargabe collector
        onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        };
        items.addOnListChangedCallback(new WeakOnListChangedCallback<>(onListChangedCallback));
    }

    @Override public BindableViewHolder<?, T> onCreateViewHolder(ViewGroup viewGroup, int i) {
        return viewHolderFactory.call(viewGroup);
    }

    @Override public void onBindViewHolder(BindableViewHolder<?, T> viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override public int getItemCount() {
        return items.size();
    }
}
