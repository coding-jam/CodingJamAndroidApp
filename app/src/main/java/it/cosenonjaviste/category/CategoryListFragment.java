package it.cosenonjaviste.category;

import android.annotation.SuppressLint;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.category.CategoryListPresenter;
import it.cosenonjaviste.mvp.category.CategoryListView;
import rx.functions.Actions;

@ParcelClasses({@ParcelClass(Category.class)})
public class CategoryListFragment extends CnjFragment<CategoryListPresenter, OptionalList<Category>> implements CategoryListView {

    @InjectView(R.id.list) SuperListview list;

    private CategoryAdapter adapter;

    @Inject MvpConfig<CategoryListView, CategoryListPresenter> config;

    @Override public MvpConfig<CategoryListView, CategoryListPresenter> getConfig() {
        return config;
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @SuppressLint("ResourceAsColor") @Override protected void initView(View view) {
        super.initView(view);
        adapter = new CategoryAdapter(getActivity());
        list.setAdapter(adapter);
        list.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        list.setOnItemClickListener((parent, v, position, id) -> presenter.goToPosts(position));
    }

    @Override protected void loadOnFirstStart() {
        presenter.loadData(0);
    }

    @Override public void update(OptionalList<Category> model) {
        model.call(
                categories -> {
                    list.showList();
                    adapter.reloadData(categories);
                }
        ).whenError(
                t -> list.showError()
        ).whenEmpty(
                Actions.empty()
        );
    }

    @Override public void startLoading() {
        list.showProgress();
    }
}
