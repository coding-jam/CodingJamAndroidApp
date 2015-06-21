package it.cosenonjaviste.author;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(AuthorListModel.class)
public class AuthorListFragment extends RecyclerViewRxMvpFragment<Author> implements AuthorListView {

    @Inject AuthorListPresenter presenter;

    @Override public void init() {
        createComponent(() -> DaggerAuthorListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build())
                .inject(this);
    }

    @Override @NonNull protected AuthorViewHolder createViewHolder(LayoutInflater inflater, CircleTransform transformation, ViewGroup v) {
        return new AuthorViewHolder(inflater.inflate(R.layout.author_cell, v, false), transformation, presenter);
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadAuthors();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
