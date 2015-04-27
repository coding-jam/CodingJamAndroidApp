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
import it.cosenonjaviste.ObjectsMapRetainedFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.MvpView;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;
import rx.functions.Actions;

public class AuthorListFragment extends RxMvpFragment<AuthorListModel> {

    @InjectView(R.id.grid) SuperGridview grid;

    @Inject AuthorListPresenter presenter;

    private AuthorAdapter adapter;

    @Override public void onCreate(Bundle state) {
        ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                () -> DaggerAuthorListComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
        super.onCreate(state);
    }

    @Override public AuthorListPresenter getPresenter() {
        return presenter;
    }

    @SuppressLint("ResourceAsColor") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_grid, container, false);
        ButterKnife.inject(this, view);
        adapter = new AuthorAdapter(getActivity());
        grid.getList().setNumColumns(2);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener((parent, v, position, id) -> presenter.goToAuthorDetail(position));
        return view;
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadAuthors();
    }

    @Override public void update(AuthorListModel model) {
        model.call(authors -> {
            grid.showList();
            adapter.reloadData(authors);
        }).whenError(t -> grid.showError()).whenEmpty(Actions.empty());
    }

    public void startLoading() {
        grid.showProgress();
    }

    public <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model) {
        SingleFragmentActivity.open(getActivity(), viewClass, model);
    }
}
