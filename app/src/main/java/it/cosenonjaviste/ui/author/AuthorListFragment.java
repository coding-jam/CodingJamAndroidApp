package it.cosenonjaviste.ui.author;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Provider;

import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.cosenonjaviste.core.author.AuthorListViewModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.Navigator;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;
import rx.Subscription;

public class AuthorListFragment extends Fragment {

    @RetainedObjectProvider("viewModel") @Inject Provider<AuthorListViewModel> provider;

    AuthorListViewModel viewModel;

    @Inject Navigator navigator;

    private Subscription subscription;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        CoseNonJavisteApp.getComponent(this).inject(this);
        LifeCycleBinder.bind(this);
        subscription = viewModel.postListNavigationEvents.subscribe(
                arg -> navigator.openPostList(getActivity(), arg));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .gridLayoutManager(2)
                .viewHolder(AuthorCellBinding::inflate, AuthorCellBinding::setAuthor, viewModel::goToAuthorDetail)
                .getRoot();
    }
}
