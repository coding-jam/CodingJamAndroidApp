package it.cosenonjaviste.core.author;

import it.cosenonjaviste.core.model.Author;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.lib.mvp.RxMvpListView;

public interface AuthorListView extends RxMvpListView<Author> {
    void openPostList(PostListModel model);
}
