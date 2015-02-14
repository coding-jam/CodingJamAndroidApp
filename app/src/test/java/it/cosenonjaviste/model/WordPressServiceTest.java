package it.cosenonjaviste.model;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WordPressServiceTest {

    @Inject Gson gson;

    @Before
    public void init() throws IOException {
        ObjectGraph.create(new TestModule()).inject(this);
    }

    @Test
    public void testLoadPosts() throws IOException {
        PostResponse postResponse = gson.fromJson(JsonStubs.getPostList(1), PostResponse.class);
        List<Post> posts = postResponse.getPosts();
        assertEquals(1, posts.size());
        Post post = posts.get(0);
        assertEquals(11213, post.getId());
        assertNotNull(post.getDate());
        assertNotNull(post.getTitle());
        assertNotNull(post.getUrl());
        assertNotNull(post.getAuthor());
        assertNotNull(post.getAuthor().getImageUrl());
        assertEquals(8, post.getAuthor().getId());
        assertNotNull(post.getAuthor().getName());
    }

    @Module(injects = WordPressServiceTest.class, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}
