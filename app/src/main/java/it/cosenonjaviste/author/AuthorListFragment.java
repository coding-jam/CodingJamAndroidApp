package it.cosenonjaviste.author;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(AuthorListModel.class)
public class AuthorListFragment extends RecyclerViewRxMvpFragment<Author> implements AuthorListView {

    @Inject AuthorListPresenter presenter;

    @Override public void init() {
        CoseNonJavisteApp.createComponent(this,
                c -> DaggerAuthorListComponent.builder().applicationComponent(c).build()
        ).inject(this);
    }

    @Override @NonNull protected AuthorViewHolder createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new AuthorViewHolder(AuthorCellBinding.inflate(inflater, v, false), presenter);
    }

    @Override protected void retry() {
        presenter.loadAuthors();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
