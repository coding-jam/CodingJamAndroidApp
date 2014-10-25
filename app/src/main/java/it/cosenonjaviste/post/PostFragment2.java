package it.cosenonjaviste.post;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment2;
import it.cosenonjaviste.R;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.mvp.post.PostListPresenter2;
import it.cosenonjaviste.mvp.post.PostListView;

public class PostFragment2 extends CnjFragment2<PostListModel, PostListView, PostListPresenter2> implements PostListView {

    @Inject Provider<PostListPresenter2> presenterProvider;

    @InjectView(R.id.list) SuperListview list;

    private PostAdapter adapter;

    @Override protected PostListView getMvpView() {
        return this;
    }

    @Override protected PostListPresenter2 createPresenter() {
        return presenterProvider.get();
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new PostAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(() -> presenter.listPosts(0));
        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToDetail(adapter.getItem(position)));
    }

    @Override public void startLoading() {
        list.showProgress();
    }

    @Override public void update(PostListModel model) {
        if (model.getPostsModel().getThrowable() != null) {
            list.showError();
        } else {
            list.showList();
            adapter.reloadData(model.getPosts());
        }
    }
}
