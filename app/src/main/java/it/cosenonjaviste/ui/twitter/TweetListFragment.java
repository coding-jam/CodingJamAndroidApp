package it.cosenonjaviste.ui.twitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.core.twitter.TweetListView;
import it.cosenonjaviste.core.twitter.TweetListViewModel;
import it.cosenonjaviste.databinding.TweetRowBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class TweetListFragment extends ViewModelFragment<TweetListViewModel> implements TweetListView {

    @Override protected TweetListViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getTweetListViewModel();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .viewHolder(TweetRowBinding::inflate, TweetRowBinding::setTweet)
                .loadMoreListener(viewModel::loadNextPage)
                .getRoot();
    }
}