package it.cosenonjaviste.author;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperGridview;

import javax.inject.Inject;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import rx.functions.Actions;

public class AuthorListFragment extends CnjFragment<AuthorListPresenter, OptionalList<Author>> implements AuthorListView {

    @InjectView(R.id.grid) SuperGridview grid;

    private AuthorAdapter adapter;

    @Inject MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> config;

    @Override public MvpConfig<OptionalList<Author>, AuthorListView, AuthorListPresenter> getConfig() {
        return config;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_grid;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new AuthorAdapter(getActivity());
        grid.getList().setNumColumns(2);
        grid.setAdapter(adapter);
//        grid.setOnItemClickListener((parent, v, position, id) -> presenter.goToDetail(adapter.getItem(position)));
    }

    @Override protected void loadOnFirstStart() {
        presenter.loadAuthors();
    }

    @Override public void update(OptionalList<Author> model) {
        model.call(authors -> {
            grid.showList();
            adapter.reloadData(authors);
        }).whenError(t -> grid.showError()).whenEmpty(Actions.empty());
    }

    @Override public void startLoading() {
        grid.showProgress();
    }
}
