package it.cosenonjaviste.core.author;

import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Author;

public interface AuthorListView extends RxMvpListView<Author> {
    void openPostList(PostListModel model);
}
