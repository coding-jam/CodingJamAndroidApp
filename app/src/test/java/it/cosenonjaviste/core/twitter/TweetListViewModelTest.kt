package it.cosenonjaviste.core.twitter

import it.cosenonjaviste.TestData
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.Tweet
import it.cosenonjaviste.model.TwitterService
import it.cosenonjaviste.ui.twitter.TweetListFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import rx.Observable

class TweetListViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var twitterService: TwitterService

    @InjectFromComponent(TweetListFragment::class) lateinit var viewModel: TweetListViewModel

    @Test fun testLoadTweets() {
        Mockito.`when`(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets(10))

        val model = viewModel.initAndResume()

        assertThat<Tweet>(model.getItems()).hasSize(10)
    }

    @Test fun testRetryAfterError() {
        Mockito.`when`(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(Observable.error<List<Tweet>>(RuntimeException()))

        val model = viewModel.initAndResume()

        assertThat(viewModel.isError.get()).isTrue()

        Mockito.`when`(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets(10))

        viewModel.reloadData()

        assertThat(viewModel.isError.get()).isFalse()
        assertThat<Tweet>(model.getItems()).hasSize(10)
    }

    @Test fun testLoadMoreTweets() {
        Mockito.`when`(twitterService.loadTweets(Matchers.eq(1)))
                .thenReturn(TestData.tweets(20))
        Mockito.`when`(twitterService.loadTweets(Matchers.eq(2)))
                .thenReturn(TestData.tweets(8))

        val tweetListModel = viewModel.initAndResume()

        assertThat<Tweet>(tweetListModel.getItems()).hasSize(20)

        viewModel.loadNextPage()

        assertThat<Tweet>(tweetListModel.getItems()).hasSize(28)
    }
}
