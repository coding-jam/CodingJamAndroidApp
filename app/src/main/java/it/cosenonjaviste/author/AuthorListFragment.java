package it.cosenonjaviste.author;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperGridview;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.mvp.author.AuthorListModel;
import it.cosenonjaviste.mvp.author.AuthorListMvpConfig;
import it.cosenonjaviste.mvp.author.AuthorListPresenter;
import it.cosenonjaviste.mvp.author.AuthorListView;

@ParcelClasses({@ParcelClass(AuthorListModel.class)})
public class AuthorListFragment extends CnjFragment<AuthorListPresenter, AuthorListModel> implements AuthorListView {

    @InjectView(R.id.grid) SuperGridview grid;

    private AuthorAdapter adapter;

    @Inject AuthorListMvpConfig config;

    @Override public AuthorListMvpConfig getConfig() {
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

    @Override public void update(AuthorListModel model) {
        model.getAuthors()
                .call(authors -> {
                    grid.showList();
                    adapter.reloadData(authors);
                })
                .whenError(t -> grid.showError())
                .whenEmpty(() -> {
                });
    }

    @Override public void startLoading() {
        grid.showProgress();
    }
}
