package it.cosenonjaviste.androidtest.base;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.mockito.MockitoAnnotations;

import dagger.ObjectGraph;
import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphCreator;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.SchedulerManager;
import rx.schedulers.Schedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class BaseActivityTest<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    private final boolean injectTest;

    public BaseActivityTest(Class<T> activityClass) {
        this(activityClass, false);
    }

    public BaseActivityTest(Class<T> activityClass, boolean injectTest) {
        super(activityClass);
        this.injectTest = injectTest;
    }

    public void setUp() throws Exception {
        super.setUp();
        Intent activityIntent = createActivityIntent();
        if (activityIntent != null) {
            setActivityIntent(activityIntent);
        }
        setupDexmaker();

        MockitoAnnotations.initMocks(this);

        final EspressoExecutor espressoExecutor = EspressoExecutor.newCachedThreadPool();

        ObjectGraphHolder.forceObjectGraphCreator(new ObjectGraphCreator() {
            @Override public ObjectGraph create(DaggerApplication app) {
                Object[] modules = mergeArrays(app.getModules(), getTestModules());
                ObjectGraph objectGraph = ObjectGraph.create(modules);
                if (injectTest) {
                    objectGraph.inject(BaseActivityTest.this);
                    initAfterInject();
                }
                return objectGraph;
            }
        });

        registerIdlingResources(espressoExecutor);

        SchedulerManager.setIo(Schedulers.from(espressoExecutor));

        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    protected Intent createActivityIntent() {
        return null;
    }

    protected void initAfterInject() {
    }

    @Override public T getActivity() {
        return super.getActivity();
    }

    private Object[] mergeArrays(Object[] appModules, Object[] testModules) {
        Object[] modules = new Object[testModules.length + appModules.length];
        System.arraycopy(appModules, 0, modules, 0, appModules.length);
        System.arraycopy(testModules, 0, modules, appModules.length, testModules.length);
        return modules;
    }

    protected Object[] getTestModules() {
        return new Object[0];
    }

    /**
     * Workaround for Mockito and JB-MR2 incompatibility to avoid
     * java.lang.IllegalArgumentException: dexcache == null
     *
     * @see <a href="https://code.google.com/p/dexmaker/issues/detail?id=2">
     * https://code.google.com/p/dexmaker/issues/detail?id=2</a>
     */
    private void setupDexmaker() {
        // Explicitly set the Dexmaker cache, so tests that use mockito work
        final String dexCache = getInstrumentation().getTargetContext().getCacheDir().getPath();
        System.setProperty("dexmaker.dexcache", dexCache);
    }

    protected void clickOnView(int id) {
        onView(withId(id)).perform(scrollTo(), click());
    }

    protected void clickOnViewNoScroll(int id) {
        onView(withId(id)).perform(click());
    }

    protected void enterText(int id, String text) {
        onView(withId(id)).perform(scrollTo(), typeText(text));
    }

    protected void showUi() {
//        if (BuildConfig.ESPRESSO_DEBUG)
//        {
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        }
    }
}
