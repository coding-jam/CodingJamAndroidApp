package it.cosenonjaviste.core.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WordPressServiceTest {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Test
    public void testLoadPosts() throws IOException {
        it.cosenonjaviste.core.model.PostResponse postResponse = gson.fromJson(JsonStubs.getPostList(1), PostResponse.class);
        List<it.cosenonjaviste.core.model.Post> posts = postResponse.getPosts();
        assertEquals(1, posts.size());
        it.cosenonjaviste.core.model.Post post = posts.get(0);
        assertEquals(12831, post.getId());
        assertNotNull(post.getDate());
        assertNotNull(post.getTitle());
        assertNotNull(post.getUrl());
        assertNotNull(post.getAuthor());
        assertNotNull(post.getAuthor().getImageUrl());
        assertEquals(2, post.getAuthor().getId());
        assertNotNull(post.getAuthor().getName());
        assertThat(post.getAttachments()).isNotEmpty();
        assertThat(post.getAttachments()[0].getUrl()).isNotEmpty();
    }
}
