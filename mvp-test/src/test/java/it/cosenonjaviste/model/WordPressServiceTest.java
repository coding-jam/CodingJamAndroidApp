package it.cosenonjaviste.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.stubs.JsonStubs;
import it.cosenonjaviste.stubs.MockWebServerWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WordPressServiceTest {

    @Inject MockWebServerWrapper server;

    @Inject WordPressService service;

    @Before
    public void init() throws IOException {
        TestComponent component = Dagger_TestComponent.builder().build();
        component.inject(this);
//        ObjectGraph.create(new MvpTestModule(), new TestModule()).inject(this);
        server.initDispatcher(JsonStubs.getPostList(1));
    }

    @Test
    public void testLoad() throws IOException {
        service.listPosts(0).subscribe(repoResponse -> {
            List<Post> posts = repoResponse.getPosts();
            assertEquals(1, posts.size());
            Post post = posts.get(0);
            assertEquals(11213, post.getId());
            assertNotNull(post.getDate());
            assertNotNull(post.getTitle());
            assertNotNull(post.getUrl());
            assertNotNull(post.getAuthor());
            assertEquals(8, post.getAuthor().getId());
            assertNotNull(post.getAuthor().getName());
        });
    }

    @After
    public void shutdown() throws IOException {
        server.shutdown();
    }

    @Module(injects = WordPressServiceTest.class, addsTo = MvpTestModule.class)
    public static class TestModule {
    }

    @Component()
//    @Component(dependencies = MvpTestModule.class)
    public interface TestComponent {
        void inject(WordPressServiceTest test);
    }

    @After public void shutdownServer() throws IOException {
        server.shutdown();
    }
}
