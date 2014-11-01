package it.cosenonjaviste.mvp.post;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.mvp.ListPresenter;
import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.MvpConfig;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import rx.Observable;

public class PostListPresenter extends ListPresenter<Post> {

    private static final String CATEGORY = "category";

    @Inject WordPressService wordPressService;

    @Inject MvpConfig<PostDetailModel, PostDetailView, PostDetailPresenter> postDetailMvpConfig;

    @Override public OptionalList<Post> createModel(PresenterArgs args) {
        PostListModel postListModel = new PostListModel();
        postListModel.setCategory(args.getObject(CATEGORY));
        return postListModel;
    }

    @Override protected Observable<List<Post>> getObservable(int page) {
        return wordPressService.listPosts(page).map(PostResponse::getPosts);
    }

    public void goToDetail(Post item) {
        PostDetailPresenter.open(contextBinder, postDetailMvpConfig, item);
    }

    public static PresenterArgs open(ContextBinder contextBinder, Category category) {
        PresenterArgs args = contextBinder.createArgs();
        args.putObject(CATEGORY, category);
        return args;
    }
}
