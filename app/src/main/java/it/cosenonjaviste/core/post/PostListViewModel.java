package it.cosenonjaviste.core.post;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;

public class PostListViewModel extends RxListViewModel<PostListArgument, PostListModel> {

    @Inject WordPressService wordPressService;

    @Inject Navigator navigator;

    @Inject public PostListViewModel() {
    }

    @NonNull @Override protected PostListModel createModel() {
        return new PostListModel();
    }

    @Override protected void reloadData(ObservableBoolean loadingAction) {
        subscribe(loadingAction::set,
                getObservable(1),
                posts -> {
                    model.done(posts);
                    model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                }, throwable -> model.error());
    }

    public void goToDetail(int position) {
        Post item = model.getItems().get(position);
        navigator.openDetail(item);
    }

    public void loadNextPage() {
        if (!loadingNextPage.get() && model.isMoreDataAvailable()) {
            int page = calcNextPage(model.getItems().size(), WordPressService.POST_PAGE_SIZE);
            Observable<List<Post>> observable = getObservable(page);

            subscribe(loadingNextPage::set,
                    observable,
                    posts -> {
                        model.append(posts);
                        model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    },
                    throwable -> model.error());
        }
    }

    private Observable<List<Post>> getObservable(int page) {
        Observable<PostResponse> observable;
        if (getArgument() == null) {
            observable = wordPressService.listPosts(page);
        } else {
            Category category = getArgument().category();
            if (category != null) {
                observable = wordPressService.listCategoryPosts(category.id(), page);
            } else {
                Author author = getArgument().author();
                observable = wordPressService.listAuthorPosts(author.id(), page);
            }
        }
        return observable.map(PostResponse::posts);
    }

    private static int calcNextPage(int size, int pageSize) {
        return size / pageSize + 1;
    }

    public boolean isToolbarVisible() {
        PostListArgument arg = getArgument();
        return arg != null && (arg.author() != null || arg.category() != null);
    }

    public String getToolbarTitle() {
        PostListArgument arg = getArgument();
        if (arg == null) {
            return null;
        } else {
            Author author = arg.author();
            if (author != null) {
                return author.name();
            } else {
                Category category = arg.category();
                if (category != null) {
                    return category.title();
                } else {
                    return null;
                }
            }
        }
    }
}
