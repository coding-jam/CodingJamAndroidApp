package it.cosenonjaviste.author;

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
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;

public class AuthorViewHolder extends BindableViewHolder<Author> {

    private final CircleTransform transformation;
    private final AuthorListPresenter presenter;

    @InjectView(R.id.first_name) TextView firstName;
    @InjectView(R.id.last_name) TextView lastName;
    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.author_image) ImageView image;

    private int position;

    public AuthorViewHolder(View itemView, CircleTransform transformation, AuthorListPresenter presenter) {
        super(itemView);
        this.transformation = transformation;
        this.presenter = presenter;
        ButterKnife.inject(this, itemView);
    }

    @OnClick(R.id.author_cell) void goToDetail() {
        presenter.goToAuthorDetail(position);
    }

    @Override public void bind(Author author, int position) {
        this.position = position;
        String description = author.getDescription();
        title.setText(Html.fromHtml(description.replaceAll("^<p>", "").replaceAll("$</p>", "")));
        firstName.setText(author.getFirstName());
        lastName.setText(author.getLastName());
        String imageUrl = author.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(itemView.getContext()).load(imageUrl).transform(transformation).into(image);
        }
    }
}
