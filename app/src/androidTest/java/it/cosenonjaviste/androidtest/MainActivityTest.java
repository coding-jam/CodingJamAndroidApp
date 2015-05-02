package it.cosenonjaviste.androidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.MainActivity;
import it.cosenonjaviste.R;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.DaggerTestComponent;
import it.cosenonjaviste.androidtest.base.MockWebServerWrapper;
import it.cosenonjaviste.androidtest.base.TestComponent;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Inject WordPressService wordPressService;

    @Inject MockWebServerWrapper server;

    @Inject TwitterService twitterService;

    @Rule public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestComponent.builder().build();
        ((CoseNonJavisteApp) app).setComponent(component);

        component.inject(this);

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(10));
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));
        when(wordPressService.listAuthors())
                .thenReturn(TestData.authorResponse(2));
        when(wordPressService.listAuthorPosts(anyLong(), anyInt()))
                .thenReturn(TestData.postResponse(1));

        when(twitterService.loadTweets(eq(1)))
                .thenReturn(TestData.tweets());

        server.initDispatcher("<html><body>CoseNonJaviste</body></html>");
    }

    @Test public void showMainActivity() {
        activityRule.launchActivity(null);
    }

    @Test public void showCategories() {
        activityRule.launchActivity(null);
        clickOnDrawer(1);
        onView(withText("cat 0")).check(matches(isDisplayed()));
    }

    @Test public void showAuthors() {
        activityRule.launchActivity(null);
        clickOnDrawer(2);
        onView(withText("name 0")).check(matches(isDisplayed()));
    }

    @Test public void showTweets() {
        activityRule.launchActivity(null);
        clickOnDrawer(3);
        onView(withText("tweet text 1")).check(matches(isDisplayed()));
    }

    @Test public void showContactForm() {
        activityRule.launchActivity(null);
        clickOnDrawer(4);
    }

    private void clickOnDrawer(int position) {
        onView(withClassName(endsWith("ImageButton"))).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.left_drawer))
                .atPosition(position).perform(click());
    }
}
