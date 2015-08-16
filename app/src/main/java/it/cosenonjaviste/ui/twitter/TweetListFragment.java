package it.cosenonjaviste.ui.twitter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.twitter.TweetListPresenter;
import it.cosenonjaviste.core.twitter.TweetListView;
import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.BindableViewHolder;
import it.cosenonjaviste.ui.utils.RecyclerViewRxMvpFragment;

public class TweetListFragment extends RecyclerViewRxMvpFragment<TweetListPresenter, Tweet> implements TweetListView {

    @Override protected TweetListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getTweetListPresenter();
    }

    @NonNull @Override protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.setPresenter(presenter);
        presenter.setListChangeListener(adapter::reloadData);
        return view;
    }

    @Override protected void loadMoreItems() {
        presenter.loadNextPage();
    }

    @NonNull @Override protected BindableViewHolder<Tweet> createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new TweetViewHolder(TweetRowBinding.bind(inflater.inflate(R.layout.tweet_row, v, false)));
    }

}