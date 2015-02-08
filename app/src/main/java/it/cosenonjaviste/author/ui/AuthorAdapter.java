package it.cosenonjaviste.author.ui;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.utils.CircleTransform;

public class AuthorAdapter extends BaseAdapter {

    private List<Author> authors = new ArrayList<>();

    private Context context;
    private CircleTransform transformation;

    public AuthorAdapter(Context context) {
        this.context = context;
        transformation = CircleTransform.createWithBorder(context, R.dimen.author_image_size_big, R.color.colorPrimary, R.dimen.author_image_border);
    }

    @Override public int getCount() {
        return authors.size();
    }

    @Override public Author getItem(int position) {
        return authors.get(position);
    }

    @Override public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.author_cell, parent, false);
            RowWrapper rowWrapper = new RowWrapper();
            ButterKnife.inject(rowWrapper, convertView);
            convertView.setTag(rowWrapper);
        }
        RowWrapper rowWrapper = (RowWrapper) convertView.getTag();
        Author author = getItem(position);
        String description = author.getDescription();
        rowWrapper.title.setText(Html.fromHtml(description.replaceAll("^<p>", "").replaceAll("$</p>", "")));
        rowWrapper.firstName.setText(author.getFirstName());
        rowWrapper.lastName.setText(author.getLastName());
        String imageUrl = author.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).transform(transformation).into(rowWrapper.image);
        }
        return convertView;
    }

    public void reloadData(List<Author> posts) {
        this.authors = posts;
        notifyDataSetChanged();
    }

    static class RowWrapper {
        @InjectView(R.id.first_name) TextView firstName;
        @InjectView(R.id.last_name) TextView lastName;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.author_image) ImageView image;
    }
}
