package it.cosenonjaviste.twitter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;

@ParcelClass(TweetListModel.class)
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
        binding.swipeRefresh.setOnRefreshListener(presenter::loadDataPullToRefresh);
        presenter.setListChangeListener(adapter::reloadData);
        return view;
    }

    @Override protected void loadMoreItems() {
        presenter.loadNextPage();
    }

    @NonNull @Override protected BindableViewHolder<Tweet> createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new TweetViewHolder(TweetRowBinding.bind(inflater.inflate(R.layout.tweet_row, v, false)));
    }

    @Override protected void retry() {
        presenter.reloadData();
    }
}