package it.cosenonjaviste.core.page;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.Attachment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public class PageViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @Mock Navigator navigator;

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
        viewModel.initAndResume(TestData.createPost(1));

        viewModel.share();

        verify(navigator).share(anyString(), anyString());
    }
}