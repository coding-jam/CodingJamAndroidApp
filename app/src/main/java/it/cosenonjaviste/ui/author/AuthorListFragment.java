package it.cosenonjaviste.ui.author;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.core.author.AuthorListView;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class AuthorListFragment extends RecyclerViewRxMvpFragment<AuthorListPresenter, Author> implements AuthorListView {

    @Override protected AuthorListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getAuthorListPresenter();
    }

    @Override @NonNull protected AuthorViewHolder createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new AuthorViewHolder(AuthorCellBinding.bind(inflater.inflate(R.layout.author_cell, v, false)), presenter);
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
