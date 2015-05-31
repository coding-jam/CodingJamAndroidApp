package it.cosenonjaviste.twitter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.DateFormatter;

public class TweetViewHolder extends BindableViewHolder<Tweet> {

    private final CircleTransform transformation;

    @InjectView(R.id.date) TextView date;
    @InjectView(R.id.author) TextView author;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.author_image) ImageView image;

    public TweetViewHolder(View itemView, CircleTransform transformation) {
        super(itemView);
        this.transformation = transformation;
        ButterKnife.inject(this, itemView);
    }

    @Override public void bind(Tweet item, int position) {
        title.setText(item.getText());
        author.setText(item.getAuthor());
        date.setText(DateFormatter.formatDate(item.getCreatedAt()));
        String imageUrl = item.getUserImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(itemView.getContext()).load(imageUrl).transform(transformation).into(image);
        }
    }
}
