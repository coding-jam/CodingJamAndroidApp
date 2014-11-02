package it.cosenonjaviste.twitter;

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
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Tweet;

public class TweetAdapter extends BaseAdapter {

    private List<Tweet> items = new ArrayList<>();

    private Context context;

    public TweetAdapter(Context context) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.category_row, parent, false);
            RowWrapper rowWrapper = new RowWrapper();
            ButterKnife.inject(rowWrapper, convertView);
            convertView.setTag(rowWrapper);
        }
        RowWrapper rowWrapper = (RowWrapper) convertView.getTag();
        Tweet item = getItem(position);
        rowWrapper.title.setText(item.getText());
//        rowWrapper.subtitle.setText(context.getString(R.string.post_count, item.getPostCount()));
        return convertView;
    }

    public void reloadData(List<Tweet> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class RowWrapper {
        @InjectView(R.id.category_name) TextView title;
        @InjectView(R.id.category_posts) TextView subtitle;
    }
}
