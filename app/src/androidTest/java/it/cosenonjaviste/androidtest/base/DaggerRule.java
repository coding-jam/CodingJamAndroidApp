package it.cosenonjaviste.androidtest.base;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import it.cosenonjaviste.CoseNonJavisteApp;
import rx.functions.Action1;

public class DaggerRule implements TestRule {

    private Action1<TestComponent> afterInjectAction;

    public DaggerRule(Action1<TestComponent> afterInjectAction) {
        this.afterInjectAction = afterInjectAction;
    }

    @Override public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {

                Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
                TestComponent component = DaggerTestComponent.builder().build();
                ((CoseNonJavisteApp) app).setComponent(component);
                if (afterInjectAction != null) {
                    afterInjectAction.call(component);
                }

                base.evaluate();
            }
        };
    }
}
