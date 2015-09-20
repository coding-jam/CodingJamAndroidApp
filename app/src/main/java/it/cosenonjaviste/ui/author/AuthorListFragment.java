package it.cosenonjaviste.ui.author;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class AuthorListFragment extends ViewModelFragment<AuthorListViewModel> {

    @Override public AuthorListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getAuthorListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .gridLayoutManager(2)
                .viewHolder(AuthorCellBinding::inflate, AuthorCellBinding::setAuthor, vh -> viewModel.goToAuthorDetail(vh.getAdapterPosition()))
                .getRoot();
    }
}
