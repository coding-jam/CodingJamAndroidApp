package it.cosenonjaviste;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.utils.CircleTransform;

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
        rowWrapper.title.setText(post.getTitle());
        String excerpt = post.getExcerpt() != null ? post.getExcerpt() : "";
        rowWrapper.text.setText(Html.fromHtml(excerpt.replaceAll("^<p>", "").replaceAll("$</p>", "")));
        rowWrapper.author.setText(post.getAuthor().getName());
        rowWrapper.date.setText(DateUtils.getRelativeTimeSpanString(context, post.getDate().getTime()));
        if (!TextUtils.isEmpty(post.getAuthor().getEmail())) {
            String imageUrl = "http://www.gravatar.com/avatar/" + md5Hex(post.getAuthor().getEmail());
            Picasso.with(context).load(imageUrl).transform(transformation).into(rowWrapper.image);
        }
        return convertView;
    }

    public static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte anArray : array) {
            sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (Exception ignored) {
        }
        return null;
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
