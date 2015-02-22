package it.cosenonjaviste.author;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperGridview;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.Dagger2CnjFragment;
import it.cosenonjaviste.ObjectsMapRetainedFragment;
import it.cosenonjaviste.R;
import rx.functions.Actions;

public class AuthorListFragment extends Dagger2CnjFragment<AuthorListModel> {

    @InjectView(R.id.grid) SuperGridview grid;

    @Inject AuthorListPresenter presenter;

    private AuthorAdapter adapter;

    @Override protected AuthorListPresenter injectAndCreatePresenter() {
        ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                AuthorListFragment.class.getName(),
                () -> Dagger_AuthorListComponent.builder().applicationComponent(getComponent()).build()
        ).inject(this);
        return presenter;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_grid;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new AuthorAdapter(getActivity());
        grid.getList().setNumColumns(2);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener((parent, v, position, id) -> presenter.goToAuthorDetail(position));
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
}
