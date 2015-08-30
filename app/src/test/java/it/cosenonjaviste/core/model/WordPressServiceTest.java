package it.cosenonjaviste.core.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import it.cosenonjaviste.model.Attachment;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.PostResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class WordPressServiceTest {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Test
    public void testLoadPosts() throws IOException {
        PostResponse postResponse = gson.fromJson(JsonStubs.getPostList(1), PostResponse.class);
        List<Post> posts = postResponse.getPosts();
        assertThat(posts).hasSize(1);
        Post post = posts.get(0);
        assertEquals(12831, post.getId());
        assertThat(post.getDate()).isNotNull();
        assertThat(post.getTitle()).isNotEmpty();
        assertThat(post.getUrl()).isNotEmpty();
        assertThat(post.getExcerptHtml()).isNotEmpty();
        assertThat(post.getAuthor()).isNotNull();
        assertThat(post.getAuthor().getImageUrl()).isNotEmpty();
        assertEquals(2, post.getAuthor().getId());
        assertThat(post.getAuthor().getName()).isNotEmpty();

        Attachment[] attachments = post.getAttachments();
        assertThat(attachments).hasSize(1);
        assertThat(attachments[0].getUrl()).isNotEmpty();
        assertThat(post.getImageUrl()).isNotEmpty();
    }
}
