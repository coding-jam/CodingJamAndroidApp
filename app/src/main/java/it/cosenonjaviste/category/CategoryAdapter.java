package it.cosenonjaviste.category;

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
import it.cosenonjaviste.model.Category;

public class CategoryAdapter extends BaseAdapter {

    private List<Category> authors = new ArrayList<>();

    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @Override public int getCount() {
        return authors.size();
    }

    @Override public Category getItem(int position) {
        return authors.get(position);
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
        Category category = getItem(position);
        rowWrapper.title.setText(category.getTitle());
        rowWrapper.subtitle.setText(context.getString(R.string.post_count, category.getPostCount()));
        return convertView;
    }

    public void reloadData(List<Category> posts) {
        this.authors = posts;
        notifyDataSetChanged();
    }

    static class RowWrapper {
        @InjectView(R.id.category_name) TextView title;
        @InjectView(R.id.category_posts) TextView subtitle;
    }
}
