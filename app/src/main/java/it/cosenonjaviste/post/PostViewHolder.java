package it.cosenonjaviste.post;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.DateFormatter;

public class PostViewHolder extends BindableViewHolder<Post> {

    private final CircleTransform transformation;
    private final PostListPresenter presenter;

    @InjectView(R.id.date) TextView date;
    @InjectView(R.id.author) TextView author;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.text) TextView text;
    @InjectView(R.id.author_image) ImageView image;

    private Post post;

    public PostViewHolder(View itemView, CircleTransform transformation, PostListPresenter presenter) {
        super(itemView);
        this.transformation = transformation;
        this.presenter = presenter;
        ButterKnife.inject(this, itemView);
    }

    @OnClick(R.id.post_cell) void goToDetail() {
        presenter.goToDetail(post);
    }

    @Override public void bind(Post post, int position) {
        this.post = post;
        title.setText(Html.fromHtml(post.getTitle()));
        String excerpt = post.getExcerpt() != null ? post.getExcerpt() : "";
        text.setText(Html.fromHtml(excerpt.replaceAll("^<p>", "").replaceAll("$</p>", "")));
        author.setText(post.getAuthor().getName());
        date.setText(DateFormatter.formatDate(post.getDate()));
        String imageUrl = post.getAuthor().getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(itemView.getContext()).load(imageUrl).transform(transformation).into(image);
        }
    }
}
