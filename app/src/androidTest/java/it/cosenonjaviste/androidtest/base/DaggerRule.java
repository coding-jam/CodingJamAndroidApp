package it.cosenonjaviste.androidtest.base;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.MockitoAnnotations;

import dagger.ObjectGraph;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.support.test.espresso.Espresso.registerIdlingResources;

public class DaggerRule implements TestRule {

    private Object testModule;

    private Action1<ObjectGraph> afterInjectAction;

    public DaggerRule(Object testModule, Action1<ObjectGraph> afterInjectAction) {
        this.testModule = testModule;
        this.afterInjectAction = afterInjectAction;
    }

    @Override public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {

                setupDexmaker();

                //TODO
                MockitoAnnotations.initMocks(this);

                final EspressoExecutor espressoExecutor = EspressoExecutor.newCachedThreadPool();

                ObjectGraphHolder.forceObjectGraphCreator(app -> {
                    Object[] modules = mergeArrays(app.getModules(), new Object[]{testModule});
                    ObjectGraph objectGraph = ObjectGraph.create(modules);
                    if (afterInjectAction != null) {
                        afterInjectAction.call(objectGraph);
                    }
                    return objectGraph;
                });

                registerIdlingResources(espressoExecutor);

                SchedulerManager.setIo(Schedulers.from(espressoExecutor));

                base.evaluate();

            }
        };
    }

    /**
     * Workaround for Mockito and JB-MR2 incompatibility to avoid
     * java.lang.IllegalArgumentException: dexcache == null
     *
     * @see <a href="https://code.google.com/p/dexmaker/issues/detail?id=2">
     * https://code.google.com/p/dexmaker/issues/detail?id=2</a>
     */
    private void setupDexmaker() {
//        // Explicitly set the Dexmaker cache, so tests that use mockito work
//        final String dexCache = getInstrumentation().getTargetContext().getCacheDir().getPath();
//        System.setProperty("dexmaker.dexcache", dexCache);
    }

    private Object[] mergeArrays(Object[] appModules, Object[] testModules) {
        Object[] modules = new Object[testModules.length + appModules.length];
        System.arraycopy(appModules, 0, modules, 0, appModules.length);
        System.arraycopy(testModules, 0, modules, appModules.length, testModules.length);
        return modules;
    }

}
