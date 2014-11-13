package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.page.PageModel;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.assertj.core.api.Assertions.assertThat;

public class PostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected void initAfterInject() {
        createComponent(TestComponent.class).inject(this);
        super.initAfterInject();
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        System.out.println(lastUrl);
//        int requestCount = server.getRequestCount();
//        System.out.println(recordedRequest);
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        presenter.reloadData();
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(25);
    }

    @Test
    public void testGoToDetails() {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        Post firstPost = model.get(0);

        presenter.goToDetail(firstPost);

        PageModel detailModel = getLastModel();
        String url = detailModel.getUrl();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.getUrl());
    }

    @Singleton
    @Component(modules = MvpTestModule.class)
    public interface TestComponent {
        void inject(PostListPresenterTest test);
    }
}