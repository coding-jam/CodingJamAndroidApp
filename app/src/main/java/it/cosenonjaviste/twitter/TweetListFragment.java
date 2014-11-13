package it.cosenonjaviste.twitter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.twitter.TweetListModel;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import rx.functions.Actions;

@ParcelClasses({@ParcelClass(Tweet.class)})
public class TweetListFragment extends CnjFragment<TweetListPresenter, TweetListModel> implements TweetListView {

    @InjectView(R.id.list) SuperListview list;

    private TweetAdapter adapter;

    @Inject MvpConfig<TweetListView> config;

    @Override public MvpConfig<TweetListView> getConfig() {
        return config;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new TweetAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(presenter::reloadData);
        list.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
    }

    @Override protected void loadOnFirstStart() {
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


    @Override public void startLoading(boolean showMainLoading) {
        if (showMainLoading) {
            list.showProgress();
        } else {
            list.setRefreshing(true);
        }
    }

    @Override public void startMoreItemsLoading() {
        list.showMoreProgress();
    }
}