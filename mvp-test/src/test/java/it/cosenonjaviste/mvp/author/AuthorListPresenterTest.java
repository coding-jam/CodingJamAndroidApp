package it.cosenonjaviste.mvp.author;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerUtils;

public class AuthorListPresenterTest {

    @Inject MockWebServer server;

    @Inject AuthorListPresenter presenter;

    private ObjectGraph objectGraph;

    @Before
    public void setup() {
        objectGraph = ObjectGraph.create(new MvpTestModule(), new TestModule());
        objectGraph.inject(this);

        MockWebServerUtils.initDispatcher(server, JsonStubs.AUTHORS);

    }

    @Test
    public void testLoad() {
//        AuthorListModel model = PresenterTestUtils.init(objectGraph, presenter, null, null);
//        assertNotNull(model.getAuthors());
//        assertEquals(2, model.getAuthors().size());
    }

//    @Test
//    public void testGoToDetails() {
//        presenter.listPosts(0);
//        Post firstPost = model.getPosts().get(0);
//        presenter.goToDetail(firstPost);
//        PostDetailModel detailModel = navigator.getLastModel();
//        assertNotNull(detailModel.getPost());
//        assertEquals(firstPost.getId(), detailModel.getPost().getId());
//    }

    @Module(injects = AuthorListPresenterTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }
}