package it.cosenonjaviste.author;

import it.cosenonjaviste.lib.mvp.RxMvpListView;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.post.PostListModel;

public interface AuthorListView extends RxMvpListView<Author> {
    void openPostList(PostListModel model);
}
