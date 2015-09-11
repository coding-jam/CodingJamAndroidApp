package it.cosenonjaviste.ui.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.post.PostListView;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.page.PageFragment;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class PostListFragment extends ViewModelFragment<PostListViewModel> implements PostListView {

    @Override protected PostListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getPostListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .viewHolder(PostRowBinding::inflate, PostRowBinding::setPost, vh -> viewModel.goToDetail(vh.getItem()))
                .loadMoreListener(viewModel::loadNextPage)
                .showToolbar((AppCompatActivity) getActivity(), viewModel.isToolbarVisible(), viewModel.getToolbarTitle())
                .getRoot();
    }

    @Override public void openDetail(PageModel model) {
        SingleFragmentActivity.open(getActivity(), PageFragment.class, model);
    }
}
