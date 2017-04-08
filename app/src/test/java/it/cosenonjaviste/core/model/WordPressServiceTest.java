package it.cosenonjaviste.core.model;

import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;

public class WordPressServiceTest {

    @Rule
    public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @InjectFromComponent Gson gson;

    @Test
    public void testLoadPosts() throws IOException {
        //TODO test using DevelopingConverter
//        PostResponse postResponse = gson.fromJson(JsonStubs.getPostList(1), PostResponse.class);
//        List<Post> posts = postResponse.posts();
//        assertThat(posts).hasSize(1);
//        Post post = posts.get(0);
//        assertEquals(12831, post.id());
//        assertThat(post.date()).isNotNull();
//        assertThat(post.title()).isNotEmpty();
//        assertThat(post.url()).isNotEmpty();
//        assertThat(post.excerptHtml()).isNotEmpty();
//        assertThat(post.author()).isNotNull();
//        assertThat(post.author().imageUrl()).isNotEmpty();
//        assertEquals(2, post.author().id());
//        assertThat(post.author().name()).isNotEmpty();
//
//        List<Attachment> attachments = post.attachments();
//        assertThat(attachments).hasSize(1);
//        assertThat(attachments.get(0).url()).isNotEmpty();
//        assertThat(post.imageUrl()).isNotEmpty();
    }
}
