package it.cosenonjaviste.androidtest.base;

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
                TestComponent component = DaggerUtils.getComponent();
                if (afterInjectAction != null) {
                    afterInjectAction.call(component);
                }
                CoseNonJavisteApp.component = component;

                base.evaluate();

            }
        };
    }
}
