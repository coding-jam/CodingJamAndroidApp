package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.MvpView;
import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class PostListModel {

    private static final String CATEGORY = "category";

    private static final String AUTHOR = "author";

    OptionalList items = new OptionalList<>();

    Category category;

    boolean moreDataAvailable;

    Author author;

    PostListModel() {
    }

    public PostListModel(PresenterArgs args) {
        category = args.getObject(PostListModel.CATEGORY);
        author = args.getObject(PostListModel.AUTHOR);
    }

    public PostListModel(Category category) {
        this.category = category;
    }

    public PostListModel(Author author) {
        this.author = author;
    }

    public static PresenterArgs open(MvpView<?> view, Category category) {
        PresenterArgs args = view.createArgs();
        args.putObject(CATEGORY, category);
        return args;
    }

    public static PresenterArgs open(MvpView<?> view, Author author) {
        PresenterArgs args = view.createArgs();
        args.putObject(AUTHOR, author);
        return args;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public OptionalList<Post> getItems() {
        return items;
    }
}
