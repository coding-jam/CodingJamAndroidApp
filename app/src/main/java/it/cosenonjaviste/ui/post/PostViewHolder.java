package it.cosenonjaviste.ui.post;

import android.databinding.ObservableField;

import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mv2m.recycler.BindableViewHolder;


public class PostViewHolder extends BindableViewHolder<Post> {
    public final ObservableField<Post> item = new ObservableField<>();

    private PostRowBinding binding;

    private PostListViewModel viewModel;

    public PostViewHolder(PostRowBinding binding, PostListViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        binding.setViewHolder(this);
    }

    @Override public void bind(Post item) {
        this.item.set(item);
        binding.executePendingBindings();
    }

    public void onItemClicked() {
        viewModel.goToDetail(getAdapterPosition());
    }
}
