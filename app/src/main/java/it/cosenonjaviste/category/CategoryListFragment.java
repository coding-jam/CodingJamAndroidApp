package it.cosenonjaviste.category;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(CategoryListModel.class)
public class CategoryListFragment extends RecyclerViewRxMvpFragment<Category> implements CategoryListView {

    @Inject CategoryListPresenter presenter;

    @Override public void init() {
        CoseNonJavisteApp.createComponent(this,
                c -> DaggerCategoryListComponent.builder().applicationComponent(c).build()
        ).inject(this);
    }

    @NonNull @Override protected BindableViewHolder<Category> createViewHolder(LayoutInflater inflater, CircleTransform transformation, ViewGroup v) {
        return new CategoryViewHolder(inflater.inflate(R.layout.category_row, v, false), presenter);
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadData();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
