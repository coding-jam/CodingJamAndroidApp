package it.cosenonjaviste.post;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;

@ParcelClasses({@ParcelClass(Post.class), @ParcelClass(Author.class), @ParcelClass(PostListModel.class)})
public class PostFragment extends CnjFragment<PostListPresenter, PostListModel> implements PostListView {

    @Inject Provider<PostListPresenter> presenterProvider;

    @InjectView(R.id.list) SuperListview list;

    private PostAdapter adapter;

    @Override protected PostListPresenter createPresenter() {
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

    @Override public void update(PostListModel model) {
        if (model.getPostsModel().getThrowable() != null) {
            list.showError();
        } else {
            list.showList();
            adapter.reloadData(model.getPosts());
        }
    }

    @Override public void startLoading() {
        list.showProgress();
    }
}
