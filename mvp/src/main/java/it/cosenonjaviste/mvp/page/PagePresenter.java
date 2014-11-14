package it.cosenonjaviste.mvp.page;

import javax.inject.Inject;

import it.cosenonjaviste.mvp.base.ContextBinder;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;

public class PagePresenter extends RxMvpPresenter<PageModel> {

    public static final String URL = "url";

    @Inject PageUrlManager pageUrlManager;

    @Inject public PagePresenter() {
    }

    public static void open(ContextBinder contextBinder, String url) {
        PresenterArgs args = populateArgs(contextBinder.createArgs(), url);
        contextBinder.startNewActivity(PageView.class, args);
    }

    @Override public PageModel createModel(PresenterArgs args) {
        PageModel model = new PageModel();
        model.setUrl(args.getObject(URL));
        return model;
    }

    public static PresenterArgs populateArgs(PresenterArgs args, String url) {
        args.putObject(URL, url);
        return args;
    }

    public String getPostUrl(String url) {
        return pageUrlManager.getUrl(url);
    }
}
