package it.cosenonjaviste.core.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.model.Attachment;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PageViewModelTest {

    @Mock Navigator navigator;

    @InjectMocks PageViewModel viewModel;

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

        verify(navigator).share(any(), anyString(), anyString());
    }
}