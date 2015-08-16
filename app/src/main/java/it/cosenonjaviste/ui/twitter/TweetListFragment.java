package it.cosenonjaviste.ui.twitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.twitter.TweetListPresenter;
import it.cosenonjaviste.core.twitter.TweetListView;
import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class TweetListFragment extends MvpFragment<TweetListPresenter> implements TweetListView {

    @Override protected TweetListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getTweetListPresenter();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, presenter)
                .linearLayoutManager()
                .viewHolderFactory(v -> new TweetViewHolder(TweetRowBinding.bind(inflater.inflate(R.layout.tweet_row, v, false))))
                .loadMoreListener(presenter::loadNextPage)
                .getRoot();
    }
}