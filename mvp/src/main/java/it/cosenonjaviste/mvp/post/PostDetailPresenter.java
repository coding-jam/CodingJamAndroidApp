package it.cosenonjaviste.mvp.post;

import javax.inject.Inject;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PostDetailPresenter extends RxMvpPresenter<PostDetailModel> {

    public static final String POST = "Post";

    @Inject public PostDetailPresenter() {
    }

    @Override protected PostDetailModel createModel(PresenterArgs args) {
        PostDetailModel model = new PostDetailModel();
        model.setPost(args.getObject(POST));
        return model;
    }

    public static void populateArgs(PresenterArgs args, Post item) {
        args.putObject(POST, item);
    }
}
