package org.mockito.configuration;

import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import rx.Observable;

public class MockitoConfiguration extends DefaultMockitoConfiguration {
    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues() {
            @Override public Object answer(InvocationOnMock invocation) {
                Method method = invocation.getMethod();
                Class<?> returnType = method.getReturnType();
                if (returnType.isAssignableFrom(Observable.class)) {
                    return Observable.empty();
                }
                return super.answer(invocation);
            }
        };
    }
}