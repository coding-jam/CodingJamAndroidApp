package it.cosenonjaviste;

import android.os.Bundle;
import android.view.View;

import com.quentindommerc.superlistview.SuperListview;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import it.cosenonjaviste.base.DaggerApplication;
import it.cosenonjaviste.base.ObjectGraphHolder;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import it.cosenonjaviste.mvp.PostListModel;
import it.cosenonjaviste.mvp.PostListPresenter;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class PostFragment extends RxMvpFragment<PostListPresenter, PostListModel> {

    @Inject Provider<PostListPresenter> presenterProvider;

    @InjectView(R.id.list) SuperListview list;

    private PostAdapter adapter;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override public void onCreate(Bundle savedInstanceState) {
        ObjectGraphHolder.inject((DaggerApplication) getActivity().getApplication(), this);
        super.onCreate(savedInstanceState);
    }

    @Override protected Navigator getNavigator() {
        return null;
    }

    @Override protected PostListPresenter createPresenter() {
        return presenterProvider.get();
    }

    @Override protected int getLayoutId() {
        return R.layout.super_list;
    }

    @Override protected void initView(View view) {
        super.initView(view);
        adapter = new PostAdapter(getActivity());
        list.setAdapter(adapter);
    }

    @Override protected void subscribeToModelUpdates(Observable<ModelEvent<PostListModel>> modelUpdates) {
        subscriptions.add(modelUpdates.map(ModelEvent::getModel).subscribe(this::updateView));
    }

    @Override public void onStop() {
        super.onStop();
        subscriptions.unsubscribe();
        subscriptions = new CompositeSubscription();
    }

    @Override public void updateView(PostListModel model) {
        if (model.getPosts() != null) {
            adapter.reloadData(model.getPosts());
        }
    }
}
