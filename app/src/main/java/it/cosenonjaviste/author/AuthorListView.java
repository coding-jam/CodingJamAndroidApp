package it.cosenonjaviste.author;

import it.cosenonjaviste.post.PostListModel;

public interface AuthorListView {
    void update(AuthorListModel model);

    void startLoading();

    void openPostList(PostListModel model);
}
