package it.cosenonjaviste.testableandroidapps.model;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import retrofit.RestAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MockWebServerTest {

    private MockWebServer server;

    @Before
    public void init() throws IOException {
        server = new MockWebServer();
        server.setDispatcher(new Dispatcher() {
            @Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                request.getPath();
                return new MockResponse().setBody(JSON);
            }
        });
        server.play();
    }

    @After
    public void shutdown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testLoad() throws IOException {
        URL url = server.getUrl("/");

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.toString()).build();
        WordPressService service = adapter.create(WordPressService.class);

        PostResponse repoResponse = service.listPosts().toBlocking().first();
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
    }

    private static final String JSON = "{\n" +
            "status: \"ok\",\n" +
            "count: 6,\n" +
            "count_total: 183,\n" +
            "pages: 31,\n" +
            "posts: [\n" +
            "{\n" +
            "id: 11213,\n" +
            "type: \"post\",\n" +
            "slug: \"decorator-patten-corretto-lambda-con-java-8\",\n" +
            "url: \"http://www.cosenonjaviste.it/decorator-patten-corretto-lambda-con-java-8/\",\n" +
            "status: \"publish\",\n" +
            "title: \"Decorator patten corretto lambda con Java 8\",\n" +
            "date: \"2014-05-29 10:30:36\",\n" +
            "modified: \"2014-08-11 07:35:33\",\n" +
            "categories: [\n" +
            "\t{\n" +
            "\tid: 6,\n" +
            "\tslug: \"java\",\n" +
            "\ttitle: \"Java\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 0,\n" +
            "\tpost_count: 61\n" +
            "\t},\n" +
            "\t{\n" +
            "\tid: 545,\n" +
            "\tslug: \"java-8\",\n" +
            "\ttitle: \"Java 8\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 6,\n" +
            "\tpost_count: 11\n" +
            "\t},\n" +
            "\t{\n" +
            "\tid: 418,\n" +
            "\tslug: \"tutorial-2\",\n" +
            "\ttitle: \"Tutorial\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 0,\n" +
            "\tpost_count: 35\n" +
            "\t}\n" +
            "],\n" +
            "author: {\n" +
            "\tid: 8,\n" +
            "\tslug: \"giampaolo-trapasso\",\n" +
            "\tname: \"Giampaolo Trapasso\",\n" +
            "\tfirst_name: \"Giampaolo\",\n" +
            "\tlast_name: \"Trapasso\",\n" +
            "\tnickname: \"trapo\",\n" +
            "\turl: \"http://www.cosenonjaviste.it/?team=giampaolo-trapasso\",\n" +
            "\tdescription: \"aaaaa\"\n" +
            "},\n" +
            "comments: [ ],\n" +
            "attachments: [ ],\n" +
            "comment_count: 0,\n" +
            "comment_status: \"open\",\n" +
            "custom_fields: {}\n" +
            "}\n" +
            "]\n" +
            "}";
}
