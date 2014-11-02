package it.cosenonjaviste.post;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.post.PostListModel;
import it.cosenonjaviste.mvp.post.PostListPresenter;
import it.cosenonjaviste.mvp.post.PostListView;
import rx.functions.Actions;

@ParcelClasses({@ParcelClass(Post.class), @ParcelClass(Author.class), @ParcelClass(PostListModel.class)})
public class PostFragment extends CnjFragment<PostListPresenter, OptionalList<Post>> implements PostListView {

    @InjectView(R.id.list) SuperListview list;

    private PostAdapter adapter;

    @Inject MvpConfig<PostListView, PostListPresenter> config;

    @Override public MvpConfig<PostListView, PostListPresenter> getConfig() {
        return config;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new PostAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setRefreshListener(() -> presenter.loadData(0));
        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToDetail(adapter.getItem(position)));
    }

    @Override protected void loadOnFirstStart() {
        presenter.loadData(0);
    }

    @Override public void update(OptionalList<Post> model) {
        model.call(
                posts -> {
                    list.showList();
                    adapter.reloadData(posts);
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
