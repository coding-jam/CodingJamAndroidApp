package it.cosenonjaviste.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import rx.functions.Actions;

public class PostListFragment extends CnjFragment<PostListPresenter, PostListModel> {

    @InjectView(R.id.list) SuperListview list;

    private PostAdapter adapter;

    @Override protected PostListPresenter createPresenter() {
        return getComponent().getPostListProvider();
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getComponent().inject(this);
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new PostAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(presenter::reloadData);
        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToDetail(adapter.getItem(position)));
        list.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.reloadData();
    }

    @Override public void update(PostListModel model) {
        model.getItems().call(
                posts -> {
                    list.showList();
                    list.hideMoreProgress(model.isMoreDataAvailable());
                    adapter.reloadData(posts);
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
