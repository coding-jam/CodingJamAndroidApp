package it.cosenonjaviste.ui.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.core.post.PostListView;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class PostListFragment extends MvpFragment<PostListPresenter> implements PostListView {

    @Override protected PostListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getPostListPresenter();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, presenter)
                .linearLayoutManager()
                .viewHolderFactory(v -> new PostViewHolder(PostRowBinding.bind(inflater.inflate(R.layout.post_row, v, false)), presenter))
                .loadMoreListener(presenter::loadNextPage)
                .getRoot();
    }

    @Override public void openDetail(PageModel model) {
        SingleFragmentActivity.open(getActivity(), PageFragment.class, model);
    }
}
