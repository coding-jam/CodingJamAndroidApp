package it.cosenonjaviste.ui.post;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.post.PostListView;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.databinding.RecyclerBinding;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.utils.BindableViewHolder;
import it.cosenonjaviste.ui.utils.EndlessRecyclerOnScrollListener;
import it.cosenonjaviste.ui.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class PostListFragment extends RecyclerViewRxMvpFragment<PostListPresenter, Post> implements PostListView {

    @Override protected PostListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getPostListPresenter();
    }

    @NonNull @Override protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override protected void initBinding(RecyclerBinding binding) {
        super.initBinding(binding);
        binding.list.addOnScrollListener(new EndlessRecyclerOnScrollListener(presenter::loadNextPage));
    }

    @NonNull @Override protected BindableViewHolder<Post> createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new PostViewHolder(PostRowBinding.bind(inflater.inflate(R.layout.post_row, v, false)), presenter);
    }

    @Override public void openDetail(PageModel model) {
        SingleFragmentActivity.open(getActivity(), PageFragment.class, model);
    }
}
