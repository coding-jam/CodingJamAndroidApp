package it.cosenonjaviste;

import android.content.Intent;

import it.cosenonjaviste.base.BaseFragmentTest;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.post.PostDetailPresenter;
import it.cosenonjaviste.post.PostDetailFragment;

public class PostDetailTest extends BaseFragmentTest {
    public PostDetailTest() {
        super(PostDetailFragment.class, false);
    }

    @Override protected Intent createActivityIntent() {
        Post post = new Post(123, "Title", "http://www.cosenonjaviste.it/consumare-un-webservice-restful-con-jersey-e-primefaces/");
        return createIntent(super.createActivityIntent(), PostDetailPresenter::populateArgs, post);
    }

    public void testDetailFragment() throws InterruptedException {
        Thread.sleep(20000);
    }
}
