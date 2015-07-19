package it.cosenonjaviste.author;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.AuthorResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListModel;
import rx.Observable;
import rx.functions.Action1;

@PresenterScope
public class AuthorListPresenter extends RxMvpPresenter<AuthorListModel, AuthorListView> {

    @Inject WordPressService wordPressService;

    @Inject public AuthorListPresenter() {
    }

    @Override public AuthorListModel createDefaultModel() {
        return new AuthorListModel();
    }

    public void loadDataPullToRefresh() {
        reloadData(b -> getModel().loadingPullToRefresh.set(b));
    }

    public void reloadData() {
        reloadData(b -> getModel().loading.set(b));
    }

    private void reloadData(Action1<Boolean> loadingAction) {
        Observable<List<Author>> observable = wordPressService
                .listAuthors()
                .map(AuthorResponse::getAuthors)
                .doOnNext(Collections::sort)
                .finallyDo(() -> loadingAction.call(false));

        subscribe(observable,
                () -> loadingAction.call(true),
                posts -> getModel().done(posts),
                throwable -> getModel().error());
    }

    @Override public void resume() {
        super.resume();
        if (!getModel().isLoaded() && !isTaskRunning()) {
            reloadData();
        }
    }

    public void goToAuthorDetail(int position) {
        Author author = getModel().get(position);
        getView().openPostList(new PostListModel(author));
    }
}
