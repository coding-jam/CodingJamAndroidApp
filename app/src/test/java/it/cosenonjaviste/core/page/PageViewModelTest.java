package it.cosenonjaviste.core.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.model.Attachment;
import it.cosenonjaviste.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PageViewModelTest {

    @Mock PageView view;

    @InjectMocks PageViewModel viewModel;

    @Test
    public void testParcelable() {
        PageModel model = new PageModel(new Post(1, null, "title", null, "url", null, new Attachment("http://aaaa.aa")));
        ParcelableTester.check(model, PageModel.CREATOR);
    }

    @Test
    public void testLoad() {
        viewModel.initAndResume(new PageModel(new Post(1, null, "title", null, "url", null)), view);

        assertThat(viewModel.getPost().getUrl()).isEqualTo("url");
    }
}