package it.cosenonjaviste.mvc;

import org.junit.Before;
import org.junit.Test;

import it.cosenonjaviste.mvp.PostListModel;
import it.cosenonjaviste.mvp.PostListPresenter;

import static org.junit.Assert.assertNotNull;

public class PostListPresenterTest {

    private PostListPresenter presenter;
    private PostListModel model;

    @Before
    public void setup() {
//        presenter = new PostListPresenter(new RepoService(new WordPressService() {
//            @Override public Observable<PostResponse> listPosts() {
//                return Observable.just(new Po);
//            }
//        }));
//
//        model = presenter.init(new TestContextBinder(), new EmptyObjectSaver<>(), null, null);
//        presenter.subscribe();
    }

    @Test
    public void testLoad() {
        presenter.listPosts();
        assertNotNull(model.getPosts());
    }
}