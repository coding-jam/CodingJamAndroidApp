package it.cosenonjaviste.ui.author;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.author.AuthorListView;
import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.lib.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AuthorListFragment extends ViewModelFragment<AuthorListViewModel> implements AuthorListView {

    @Override protected AuthorListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getAuthorListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .gridLayoutManager(2)
                .viewHolderFactory(v -> new AuthorViewHolder(AuthorCellBinding.inflate(inflater, v, false), viewModel))
                .getRoot();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
