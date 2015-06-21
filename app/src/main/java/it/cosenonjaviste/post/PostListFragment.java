package it.cosenonjaviste.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.utils.BindableViewHolder;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(PostListModel.class)
public class PostListFragment extends RecyclerViewRxMvpFragment<Post> implements PostListView {

    @Inject PostListPresenter presenter;

    @Override public void init() {
        createComponent(
                () -> DaggerPostListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
    }

    @NonNull @Override protected RecyclerView.LayoutManager createGridLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        superRecycler.setRefreshListener(presenter::reloadData);
        superRecycler.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> presenter.loadNextPage(), 1);
        return view;
    }

    @NonNull @Override protected BindableViewHolder<Post> createViewHolder(LayoutInflater inflater, CircleTransform transformation, ViewGroup v) {
        return new PostViewHolder(inflater.inflate(R.layout.post_row, v, false), transformation, presenter);
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.reloadData();
    }

    @Override public void openDetail(PageModel model) {
        SingleFragmentActivity.open(getActivity(), PageFragment.class, model);
    }
}
