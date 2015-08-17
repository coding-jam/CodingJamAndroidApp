package it.cosenonjaviste.ui.post;

import it.cosenonjaviste.core.post.PostListPresenter;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.ui.utils.BindableViewHolder;

public class PostViewHolder extends BindableViewHolder<Post> {

    private PostRowBinding binding;

    private Post post;

    public PostViewHolder(PostRowBinding binding, PostListPresenter presenter) {
        super(binding.getRoot());
        this.binding = binding;
        binding.getRoot().setOnClickListener(v -> presenter.goToDetail(post));
    }

    @Override public void bind(Post post, int position) {
        this.post = post;
        binding.setPost(post);
    }
}
