package it.cosenonjaviste.core.mvp.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.model.Post;
import it.cosenonjaviste.core.mvp.ViewMock;
import it.cosenonjaviste.core.page.PageModel;
import it.cosenonjaviste.core.page.PagePresenter;
import it.cosenonjaviste.core.page.PageView;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PagePresenterTest {

    private ViewMock<PageView> view = new ViewMock<>(PageView.class);

    @InjectMocks PagePresenter presenter;

    @Test
    public void testLoad() {
        view.initAndResume(new PageModel(new Post(1, null, "title", null, "url", null)), presenter);

        assertThat(presenter.getPostUrl()).isEqualTo("url");
    }
}