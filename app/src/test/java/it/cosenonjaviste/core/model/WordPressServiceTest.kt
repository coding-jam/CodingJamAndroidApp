package it.cosenonjaviste.core.model

import com.google.gson.Gson
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.PostResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class WordPressServiceTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @InjectFromComponent lateinit var gson: Gson

    @Test
    @Throws(IOException::class)
    fun testLoadPosts() {
        val postResponse = gson.fromJson(JsonStubs.getPostList(1), PostResponse::class.java)
        val posts = postResponse.posts()
        assertThat(posts).hasSize(1)
        val post = posts[0]
        assertEquals(12831, post.id())
        assertThat(post.date()).isNotNull()
        assertThat(post.title()).isNotEmpty()
        assertThat(post.url()).isNotEmpty()
        assertThat(post.excerptHtml()).isNotEmpty()
        assertThat(post.author()).isNotNull()
        assertThat(post.author().imageUrl()).isNotEmpty()
        assertEquals(2, post.author().id())
        assertThat(post.author().name()).isNotEmpty()

        val attachments = post.attachments()
        assertThat(attachments).hasSize(1)
        assertThat(attachments[0].url()).isNotEmpty()
        assertThat(post.imageUrl()).isNotEmpty()
    }
}
