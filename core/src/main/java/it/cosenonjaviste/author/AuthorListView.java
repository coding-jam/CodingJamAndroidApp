package it.cosenonjaviste.author;

import it.cosenonjaviste.post.PostListModel;

public interface AuthorListView {
    void update(AuthorListModel model);

    void showError();

    void startLoading();

    void openPostList(PostListModel model);
}
