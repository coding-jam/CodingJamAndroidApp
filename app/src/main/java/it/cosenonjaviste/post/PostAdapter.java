package it.cosenonjaviste.post;

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
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.DateFormatter;

public class PostAdapter extends BaseAdapter {

    private List<Post> posts = new ArrayList<>();

    private Context context;
    private CircleTransform transformation;

    public PostAdapter(Context context) {
        this.context = context;
        transformation = CircleTransform.createWithBorder(context, R.dimen.author_image_size, R.color.colorPrimary, R.dimen.author_image_border);
    }

    @Override public int getCount() {
        return posts.size();
    }

    @Override public Post getItem(int position) {
        return posts.get(position);
    }

    @Override public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.post_row, parent, false);
            RowWrapper rowWrapper = new RowWrapper();
            ButterKnife.inject(rowWrapper, convertView);
            convertView.setTag(rowWrapper);
        }
        RowWrapper rowWrapper = (RowWrapper) convertView.getTag();
        Post post = getItem(position);
        rowWrapper.title.setText(Html.fromHtml(post.getTitle()));
        String excerpt = post.getExcerpt() != null ? post.getExcerpt() : "";
        rowWrapper.text.setText(Html.fromHtml(excerpt.replaceAll("^<p>", "").replaceAll("$</p>", "")));
        rowWrapper.author.setText(post.getAuthor().getName());
        rowWrapper.date.setText(DateFormatter.formatDate(post.getDate()));
        String imageUrl = post.getAuthor().getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).transform(transformation).into(rowWrapper.image);
        }
        return convertView;
    }

    public void reloadData(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    static class RowWrapper {
        @InjectView(R.id.date) TextView date;
        @InjectView(R.id.author) TextView author;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.text) TextView text;
        @InjectView(R.id.author_image) ImageView image;
    }
}
