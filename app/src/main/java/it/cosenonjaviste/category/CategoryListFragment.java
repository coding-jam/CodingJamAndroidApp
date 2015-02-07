package it.cosenonjaviste.category;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperGridview;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpListView;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import rx.functions.Actions;

public class CategoryListFragment extends CnjFragment<CategoryListPresenter, OptionalList<Category>> implements MvpListView<Category> {

    @InjectView(R.id.grid) SuperGridview grid;

    @Inject Provider<CategoryListPresenter> presenterProvider;

    private CategoryAdapter adapter;

    @Override protected CategoryListPresenter createPresenter() {
        return presenterProvider.get();
    }

    @Override protected int getLayoutId() {
        return R.layout.super_grid;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new CategoryAdapter(getActivity());
        grid.setAdapter(adapter);
        grid.getList().setNumColumns(2);
        grid.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        grid.setOnItemClickListener((parent, v, position, id) -> presenter.goToPosts(position));
    }

    @OnClick(R.id.error_retry) void retry() {
        presenter.loadData();
    }


    @Override public void update(OptionalList<Category> model) {
        model.call(
                categories -> {
                    grid.showList();
                    adapter.reloadData(categories);
                }
        ).whenError(
                t -> grid.showError()
        ).whenEmpty(
                Actions.empty()
        );
    }

    @Override public void startLoading() {
        grid.showProgress();
    }
}
