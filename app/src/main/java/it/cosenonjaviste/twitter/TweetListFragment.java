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

import javax.inject.Inject;

import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;

@ParcelClass(TweetListModel.class)
public class TweetListFragment extends RecyclerViewRxMvpFragment<Tweet> implements TweetListView {

    @Inject TweetListPresenter presenter;

    @Override public void init(Bundle state) {
        createComponent(
                () -> DaggerTweetListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
    }

    @NonNull @Override protected RecyclerView.LayoutManager createGridLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        superRecycler.setRefreshListener(presenter::reloadData);
        superRecycler.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
        return view;
    }

    @NonNull @Override protected BindableViewHolder<Tweet> createViewHolder(LayoutInflater inflater, CircleTransform transformation, ViewGroup v) {
        return new TweetViewHolder(inflater.inflate(R.layout.tweet_row, v, false), transformation);
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.reloadData();
    }
}