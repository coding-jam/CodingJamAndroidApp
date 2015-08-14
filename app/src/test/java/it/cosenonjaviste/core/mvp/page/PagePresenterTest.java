package it.cosenonjaviste.core.mvp.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.page.PagePresenter;
import it.cosenonjaviste.core.page.PageView;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PagePresenterTest {

    @Mock PageView view;

    @InjectMocks PagePresenter presenter;

    @Test
    public void testLoad() {
        presenter.initAndResume(new PageModel(new Post(1, null, "title", null, "url", null)), view);

        assertThat(presenter.getPostUrl()).isEqualTo("url");
    }
}