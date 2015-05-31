package it.cosenonjaviste.category;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.utils.BindableViewHolder;

public class CategoryViewHolder extends BindableViewHolder<Category> {

    private final CategoryListPresenter presenter;

    @InjectView(R.id.category_name) TextView title;
    @InjectView(R.id.category_posts) TextView subtitle;

    private int position;

    public CategoryViewHolder(View itemView, CategoryListPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        ButterKnife.inject(this, itemView);
    }

    @OnClick(R.id.category_cell) void goToDetail() {
        presenter.goToPosts(position);
    }

    @Override public void bind(Category category, int position) {
        this.position = position;
        title.setText(category.getTitle());
        subtitle.setText(itemView.getContext().getString(R.string.post_count, category.getPostCount()));
    }
}
