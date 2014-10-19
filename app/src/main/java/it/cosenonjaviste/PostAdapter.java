package it.cosenonjaviste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.testableandroidapps.model.Post;

public class PostAdapter extends BaseAdapter {

    private List<Post> posts = new ArrayList<>();

    private Context context;

    public PostAdapter(Context context) {
        this.context = context;
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
        rowWrapper.author.setText(post.getAuthor().getName());
        rowWrapper.date.setText(post.getDate());
//        Picasso.with(context).load(post.getOwner().getAvatar()).into(rowWrapper.image);
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
    }
}
