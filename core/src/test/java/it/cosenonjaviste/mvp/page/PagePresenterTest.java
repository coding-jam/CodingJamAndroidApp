package it.cosenonjaviste.mvp.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.mvp.ViewMock;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.page.PageUrlManager;
import it.cosenonjaviste.page.PageView;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PagePresenterTest {

    private ViewMock<PageView> view = new ViewMock<>(PageView.class);

    @Spy PageUrlManager pageUrlManager = new PageUrlManager();

    @InjectMocks PagePresenter presenter;

    @Test
    public void testLoad() {
        view.initAndResume(new PageModel("url"), presenter);

        assertThat(presenter.getPostUrl()).isEqualTo("url");
    }
}