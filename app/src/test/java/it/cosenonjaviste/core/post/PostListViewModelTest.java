package it.cosenonjaviste.core.post;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import static it.cosenonjaviste.TestData.postResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PostListViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @Mock WordPressService wordPressService;

    @InjectFromComponent PostListViewModel viewModel;

    @Test
    public void testLoad() throws InterruptedException {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        PostListModel model = viewModel.initAndResume();

        assertThat(model.getItems().size()).isEqualTo(1);
    }

    @Test
    public void testLoadMore() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(postResponse(6));

        PostListModel model = viewModel.initAndResume();
        viewModel.loadNextPage();

        List<Post> items = model.getItems();
        assertThat(items.size()).isEqualTo(16);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        PostListModel model = viewModel.initAndResume();
        assertThat(viewModel.isError().get()).isTrue();

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(6));

        viewModel.reloadData();

        assertThat(viewModel.isError().get()).isFalse();
        assertThat(model.getItems().size()).isEqualTo(6);
    }

    @Test
    public void testGoToDetails() {
        when(wordPressService.listPosts(eq(1)))
                .thenReturn(postResponse(1));

        AssertableSubscriber<Post> subscriber = viewModel.postNavigationEvents.test();
        PostListModel model = viewModel.initAndResume();
        Post firstPost = model.getItems().get(0);

        viewModel.goToDetail(0);

        Post detailPost = subscriber.getOnNextEvents().get(0);
        String url = detailPost.url();

        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(firstPost.url());
    }

    @Test
    public void testToolbalTitleNotVisible() {
        viewModel.initAndResume();

        assertThat(viewModel.isToolbarVisible()).isFalse();
        assertThat(viewModel.getToolbarTitle()).isNull();
    }

    @Test
    public void testToolbalTitleAuthor() {
        viewModel.initAndResume(new PostListArgument(TestData.createAuthor(1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("name 1 last name 1");
    }

    @Test
    public void testToolbalTitle() {
        viewModel.initAndResume(new PostListArgument(Category.create(123, "aaa", 1)));

        assertThat(viewModel.isToolbarVisible()).isTrue();
        assertThat(viewModel.getToolbarTitle()).isEqualTo("aaa");
    }
}