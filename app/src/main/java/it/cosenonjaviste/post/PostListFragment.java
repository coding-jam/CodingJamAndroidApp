package it.cosenonjaviste.post;

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
import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;
import rx.functions.Actions;

public class PostListFragment extends RxMvpFragment<PostListModel> {

    @InjectView(R.id.list) SuperListview list;

    @Inject PostListPresenter presenter;

    private PostAdapter adapter;

    @Override public void onCreate(Bundle state) {
        ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                () -> DaggerPostListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
        super.onCreate(state);
    }

    @Override public PostListPresenter getPresenter() {
        return presenter;
    }

    @SuppressLint("ResourceAsColor") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_list, container, false);
        ButterKnife.inject(this, view);
        adapter = new PostAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(presenter::reloadData);
        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToDetail(adapter.getItem(position)));
        list.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
        return view;
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

    public <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model) {
        SingleFragmentActivity.open(getActivity(), viewClass, model);
    }
}
