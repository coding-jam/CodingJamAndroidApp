package it.cosenonjaviste.twitter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
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
import it.cosenonjaviste.model.Tweet;
import it.cosenonjaviste.utils.CircleTransform;

public class TweetAdapter extends BaseAdapter {

    private List<Tweet> items = new ArrayList<>();

    private Context context;

    private CircleTransform transformation;

    public TweetAdapter(Context context) {
        this.context = context;
        transformation = CircleTransform.createWithBorder(context, R.dimen.author_image_size, R.color.colorPrimary, R.dimen.author_image_border);
    }

    @Override public int getCount() {
        return items.size();
    }

    @Override public Tweet getItem(int position) {
        return items.get(position);
    }

    @Override public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tweet_row, parent, false);
            RowWrapper rowWrapper = new RowWrapper();
            ButterKnife.inject(rowWrapper, convertView);
            convertView.setTag(rowWrapper);
        }
        RowWrapper rowWrapper = (RowWrapper) convertView.getTag();
        Tweet item = getItem(position);
        rowWrapper.title.setText(item.getText());
        rowWrapper.author.setText(item.getAuthor());
        rowWrapper.date.setText(DateUtils.getRelativeTimeSpanString(context, item.getCreatedAt().getTime()));
        String imageUrl = item.getUserImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).transform(transformation).into(rowWrapper.image);
        }
        return convertView;
    }

    public void reloadData(List<Tweet> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class RowWrapper {
        @InjectView(R.id.date) TextView date;
        @InjectView(R.id.author) TextView author;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.author_image) ImageView image;
    }
}
