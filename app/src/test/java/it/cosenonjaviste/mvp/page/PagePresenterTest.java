package it.cosenonjaviste.mvp.page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import it.cosenonjaviste.mvp.MvpJUnitTestModule;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.page.PagePresenter;
import it.cosenonjaviste.post.PostListModel;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PagePresenterTest {

    @Inject PagePresenter presenter;

    @Mock PageFragment view;

    @Captor ArgumentCaptor<PostListModel> modelCaptor;

    @Before
    public void setup() {
        ObjectGraph.create(new TestModule()).inject(this);
        presenter.initAndSubscribe(new PageModel("url"), view);
    }

    @Test
    public void testLoad() {
        assertThat(presenter.getPostUrl()).isEqualTo("url");
    }

    @Module(injects = {PagePresenterTest.class}, includes = MvpJUnitTestModule.class)
    public static class TestModule {
    }
}