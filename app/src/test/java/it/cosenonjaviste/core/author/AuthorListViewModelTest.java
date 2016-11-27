package it.cosenonjaviste.core.author;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.core.CnjJUnitDaggerRule;
import it.cosenonjaviste.core.ParcelableTester;
import it.cosenonjaviste.core.post.PostListArgument;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import it.cosenonjaviste.model.WordPressService;
import rx.Observable;
import rx.observers.AssertableSubscriber;

import static it.cosenonjaviste.TestData.authorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AuthorListViewModelTest {

    @Rule public final CnjJUnitDaggerRule daggerRule = new CnjJUnitDaggerRule();

    @InjectFromComponent AuthorListViewModel viewModel;

    @Mock WordPressService wordPressService;

    @Test
    public void testParcelable() {
        AuthorListModel model = new AuthorListModel();
        ParcelableTester.check(model, AuthorListModel.CREATOR);

        model.done(Arrays.asList(TestData.createAuthor(1), TestData.createAuthor(2)));
        ParcelableTester.check(model, AuthorListModel.CREATOR);
    }

    @Test
    public void testLoad() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(10));

        AuthorListModel model = viewModel.initAndResume();

        assertThat(model.size()).isEqualTo(10);
    }

    @Test
    public void testRetryAfterError() {
        when(wordPressService.listAuthors())
                .thenReturn(
                        Observable.error(new RuntimeException()),
                        authorResponse(2)
                );

        AuthorListModel model = viewModel.initAndResume();

        assertThat(model.size()).isEqualTo(0);

        viewModel.reloadData();

        assertThat(model.size()).isEqualTo(2);
    }

    @Test
    public void testGoToDetail() {
        when(wordPressService.listAuthors())
                .thenReturn(authorResponse(2));

        AssertableSubscriber<PostListArgument> subscriber = viewModel.postListNavigationEvents.test();

        AuthorListModel authorListModel = viewModel.initAndResume();

        viewModel.goToAuthorDetail(1);

        PostListArgument argument = subscriber.getOnNextEvents().get(0);
        assertThat(argument.getAuthor()).isEqualTo(authorListModel.get(1));
    }
}