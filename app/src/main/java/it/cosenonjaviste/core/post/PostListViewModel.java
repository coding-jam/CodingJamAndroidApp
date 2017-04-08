package it.cosenonjaviste.core.post;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.list.RxListViewModel;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;

public class PostListViewModel extends RxListViewModel<PostListArgument, PostListModel> {

    @Inject WordPressService wordPressService;

    @Inject @BindLifeCycle Navigator navigator;

    @Inject public PostListViewModel() {
    }

    @NonNull @Override protected PostListModel createModel() {
        return new PostListModel();
    }

    @Override protected Disposable reloadData(ObservableBoolean loadingAction, boolean forceFetch) {
        loadingAction.set(true);
        return getObservable(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.set(false))
                .subscribe(posts -> {
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

            loadingNextPage.set(true);
            disposable.add(getObservable(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(() -> loadingNextPage.set(false))
                    .subscribe(posts -> {
                        model.append(posts);
                        model.setMoreDataAvailable(posts.size() == WordPressService.POST_PAGE_SIZE);
                    }, throwable -> model.error())
            );
        }
    }

    private Single<List<Post>> getObservable(int page) {
        if (getArgument() == null) {
            return wordPressService.listPosts(page);
        } else {
            Category category = getArgument().category();
            if (category != null) {
                return wordPressService.listCategoryPosts(category.id(), page);
            } else {
                Author author = getArgument().author();
                return wordPressService.listAuthorPosts(author.id(), page);
            }
        }
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
