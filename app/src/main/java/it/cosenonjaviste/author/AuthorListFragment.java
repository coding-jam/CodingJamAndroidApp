package it.cosenonjaviste.author;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quentindommerc.superlistview.SuperGridview;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;
import rx.functions.Actions;

public class AuthorListFragment extends RxMvpFragment {

    @InjectView(R.id.grid) SuperGridview grid;

    @Inject AuthorListPresenter presenter;

    private AuthorAdapter adapter;

    @Override public void init(Bundle state) {
        createComponent(() -> DaggerAuthorListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build())
                .inject(this);

        presenter.init(this);
    }

    @SuppressLint("ResourceAsColor") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_grid, container, false);
        ButterKnife.inject(this, view);
        adapter = new AuthorAdapter(getActivity());
        grid.getList().setOnItemClickListener((parent, view1, position, id) -> presenter.goToAuthorDetail(position));
        grid.getList().setNumColumns(2);
        grid.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadAuthors();
    }

    public void update(AuthorListModel model) {
        model.call(authors -> {
            grid.showList();
            adapter.reloadData(authors);
        }).whenError(t -> grid.showError()).whenEmpty(Actions.empty());
    }

    public void startLoading() {
        grid.showProgress();
    }

    public <MM> void open(Class<?> viewClass, MM model) {
        SingleFragmentActivity.open(getActivity(), viewClass, model);
    }

}
