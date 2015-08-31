package it.cosenonjaviste.ui.post;

import it.cosenonjaviste.BR;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class PostViewHolder extends BindableViewHolder<PostRowBinding, Post> {

    public PostViewHolder(PostRowBinding binding, PostListViewModel viewModel) {
        super(binding, BR.post);
        binding.getRoot().setOnClickListener(v -> viewModel.goToDetail(item));
    }
}
