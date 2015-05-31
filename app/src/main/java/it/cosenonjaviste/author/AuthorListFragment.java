package it.cosenonjaviste.author;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.utils.BindableAdapter;
import it.cosenonjaviste.utils.CircleTransform;
import it.cosenonjaviste.utils.SingleFragmentActivity;

@ParcelClass(AuthorListModel.class)
public class AuthorListFragment extends RxMvpFragment implements AuthorListView {

    @InjectView(R.id.recycler) SuperRecyclerView superRecycler;

    @InjectView(R.id.error_root) View errorLayout;

    @Inject AuthorListPresenter presenter;

    private BindableAdapter<Author> adapter;

    @Override public void init(Bundle state) {
        createComponent(() -> DaggerAuthorListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build())
                .inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_recycler, container, false);
        ButterKnife.inject(this, view);

        CircleTransform transformation = CircleTransform.createWithBorder(getActivity(), R.dimen.author_image_size_big, R.color.colorPrimary, R.dimen.author_image_border);
        adapter = new BindableAdapter<>(v -> new AuthorViewHolder(inflater.inflate(R.layout.author_cell, v, false), transformation, presenter));
        superRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        superRecycler.setLayoutManager(layoutManager);
        return view;
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadAuthors();
    }

    @Override public void update(AuthorListModel model) {
        superRecycler.showRecycler();
        adapter.reloadData(model.getItems());
    }

    @Override public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        superRecycler.hideRecycler();
        superRecycler.hideProgress();
    }

    @Override public void startLoading() {
        superRecycler.showProgress();
        errorLayout.setVisibility(View.GONE);
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}
