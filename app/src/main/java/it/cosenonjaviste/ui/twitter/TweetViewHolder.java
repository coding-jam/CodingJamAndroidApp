package it.cosenonjaviste.ui.twitter;

import android.databinding.ObservableField;

import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.mv2m.recycler.BindableViewHolder;


public class TweetViewHolder extends BindableViewHolder<Tweet> {
    public final ObservableField<Tweet> item = new ObservableField<>();

    private TweetRowBinding binding;

    public TweetViewHolder(TweetRowBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setViewHolder(this);
    }

    @Override public void bind(Tweet item) {
        this.item.set(item);
        binding.executePendingBindings();
    }
}
