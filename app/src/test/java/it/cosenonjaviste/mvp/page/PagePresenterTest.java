package it.cosenonjaviste.mvp.page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.lib.mvp.LifeCycle;
import it.cosenonjaviste.lib.mvp.utils.RxHolder;
import it.cosenonjaviste.mvp.TestSchedulerManager;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.page.PageUrlManager;
import it.cosenonjaviste.post.PostListModel;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PagePresenterTest {

    @Mock PageFragment view;

    @Spy RxHolder rxHolder = new RxHolder(new TestSchedulerManager(), new LifeCycle());

    @Spy PageUrlManager pageUrlManager = new PageUrlManager();

    @InjectMocks PagePresenter presenter;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Before
    public void setup() {
        presenter.init(new PageModel("url"), view);
        presenter.resume();
    }

    @Test
    public void testLoad() {
        assertThat(presenter.getPostUrl()).isEqualTo("url");
    }
}