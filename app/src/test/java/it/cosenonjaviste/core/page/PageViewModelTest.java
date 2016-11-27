package it.cosenonjaviste.core.page;

import android.support.v4.util.Pair;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.Attachment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;
import rx.observers.AssertableSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

public class PageViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @InjectFromComponent PageViewModel viewModel;

    @Test
    public void testParcelable() {
        PageModel model = new PageModel();
        model.setPost(Post.create(1, Author.create(1, "name", "last", "email"), "title", null, "url", null, Attachment.create("http://aaaa.aa")));
        ParcelableTester.check(model, PageModel.CREATOR);
    }

    @Test
    public void testLoad() {
        viewModel.initAndResume(Post.create(1, Author.create(1, "name", "last", "email"), "title", null, "url", null));

        assertThat(viewModel.getPost().url()).isEqualTo("url");
    }

    @Test
    public void testShare() {
        AssertableSubscriber<Pair<String, String>> subscriber = viewModel.shareEvents.test();
        viewModel.initAndResume(TestData.createPost(1));

        viewModel.share();

        subscriber.assertValueCount(1);
    }
}