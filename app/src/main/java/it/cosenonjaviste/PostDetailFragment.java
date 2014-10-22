package it.cosenonjaviste;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Provider;

import it.cosenonjaviste.mvp.PostDetailModel;
import it.cosenonjaviste.mvp.PostDetailPresenter;
import it.cosenonjaviste.mvp.base.Navigator;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class PostDetailFragment extends CnjFragment<PostDetailPresenter, PostDetailModel> {

    @Inject Provider<PostDetailPresenter> presenterProvider;

    private TextView textView;

    @Override protected Navigator getNavigator() {
        return null;
    }

    @Override protected void subscribeToModelUpdates(Observable<ModelEvent<PostDetailModel>> modelUpdates, CompositeSubscription subscription) {
        modelUpdates.map(ModelEvent::getModel).subscribe(model -> textView.setText(model.getPost().getTitle()));
    }

    @Override protected PostDetailPresenter createPresenter() {
        return presenterProvider.get();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView = new TextView(getActivity());
        return textView;
    }

    @Override protected int getLayoutId() {
        return 0;
    }


}
