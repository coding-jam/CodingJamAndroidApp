package it.cosenonjaviste.ui.twitter;

import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class TweetViewHolder extends BindableViewHolder<Tweet> {

    private final TweetRowBinding binding;

    public TweetViewHolder(TweetRowBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override public void bind(Tweet item, int position) {
        binding.setTweet(item);
    }
}
