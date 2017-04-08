package it.cosenonjaviste.ui.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Provider;

import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.databinding.RecyclerBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class PostListFragment extends Fragment {

    @RetainedObjectProvider("viewModel") @Inject Provider<PostListViewModel> provider;

    PostListViewModel viewModel;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        CoseNonJavisteApp.getComponent(this).inject(this);
        LifeCycleBinder.bind(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerBinding binding = RecyclerBinding.inflate(inflater, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (viewModel.isToolbarVisible()) {
            binding.toolbar.setVisibility(View.VISIBLE);
            activity.setSupportActionBar(binding.toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setTitle(viewModel.getToolbarTitle());
            }
        }

        return new RecyclerBindingBuilder<>(viewModel, binding)
                .viewHolder(viewGroup -> new PostViewHolder(PostRowBinding.inflate(inflater, viewGroup, false), viewModel))
                .loadMoreListener(viewModel::loadNextPage)
                .getRoot();
    }
}
