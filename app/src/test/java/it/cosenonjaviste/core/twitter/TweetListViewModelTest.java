package it.cosenonjaviste.core.twitter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.TestData;
import it.cosenonjaviste.model.TwitterService;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class TweetListViewModelTest {

    @Mock TweetListView view;

    @Mock TwitterService twitterService;

    @InjectMocks TweetListViewModel viewModel;

    @Test public void testLoadTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel model = viewModel.initAndResume(view);

        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testRetryAfterError() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(Observable.error(new RuntimeException()));

        TweetListModel model = viewModel.initAndResume(view);

        Assertions.assertThat(viewModel.isError().get()).isTrue();

        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        viewModel.reloadData();

        Assertions.assertThat(viewModel.isError().get()).isFalse();
        Assertions.assertThat(model.getItems()).hasSize(10);
    }

    @Test public void testLoadMoreTweets() {
        Mockito.when(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets());

        TweetListModel tweetListModel = viewModel.initAndResume(view);

        viewModel.loadNextPage();

        Assertions.assertThat(tweetListModel.getItems()).hasSize(20);
    }
}
