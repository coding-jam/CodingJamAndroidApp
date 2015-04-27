package it.cosenonjaviste.twitter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quentindommerc.superlistview.SuperListview;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.ObjectsMapRetainedFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import rx.functions.Actions;

public class TweetListFragment extends RxMvpFragment<TweetListModel> {

    @InjectView(R.id.list) SuperListview list;

    private TweetAdapter adapter;

    @Inject TweetListPresenter presenter;

    @Override public void onCreate(Bundle state) {
        ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                () -> DaggerTweetListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
        super.onCreate(state);
    }

    @Override public TweetListPresenter getPresenter() {
        return presenter;
    }

    @SuppressLint("ResourceAsColor") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_list, container, false);
        ButterKnife.inject(this, view);
        adapter = new TweetAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(presenter::reloadData);
        list.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
        return view;
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.reloadData();
    }

    @Override public void update(TweetListModel model) {
        model.call(
                items -> {
                    list.showList();
                    list.hideMoreProgress(model.isMoreDataAvailable());
                    adapter.reloadData(items);
                }
        ).whenError(
                t -> list.showError()
        ).whenEmpty(
                Actions.empty()
        );
    }

    public void startLoading(boolean showMainLoading) {
        if (showMainLoading) {
            list.showProgress();
        } else {
            list.setRefreshing(true);
        }
    }

    public void startMoreItemsLoading() {
        list.showMoreProgress();
    }
}