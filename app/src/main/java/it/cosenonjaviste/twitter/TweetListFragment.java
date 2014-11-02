package it.cosenonjaviste.twitter;

import android.annotation.SuppressLint;
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
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.twitter.TweetListPresenter;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import rx.functions.Actions;

@ParcelClasses({@ParcelClass(Tweet.class)})
public class TweetListFragment extends CnjFragment<TweetListPresenter, OptionalList<Tweet>> implements TweetListView {

    @InjectView(R.id.list) SuperListview list;

    private TweetAdapter adapter;

    @Inject MvpConfig<TweetListView, TweetListPresenter> config;

    @Override public MvpConfig<TweetListView, TweetListPresenter> getConfig() {
        return config;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new TweetAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
//        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToPosts(position));
    }

    @Override protected void loadOnFirstStart() {
        presenter.loadData(0);
    }

    @Override public void update(OptionalList<Tweet> model) {
        model.call(
                items -> {
                    list.showList();
                    adapter.reloadData(items);
                }
        ).whenError(
                t -> list.showError()
        ).whenEmpty(
                Actions.empty()
        );
    }

    @Override public void startLoading() {
        list.showProgress();
    }
}