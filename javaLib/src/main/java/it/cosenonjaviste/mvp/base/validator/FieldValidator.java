package it.cosenonjaviste.mvp.base.validator;

import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class FieldValidator {

    private final Func1<String, Boolean> checkFunction;
    private final Func0<String> errorMessage;

    FieldValidator(Func1<String, Boolean> checkFunction, Func0<String> errorMessage) {
        this.checkFunction = checkFunction;
        this.errorMessage = errorMessage;
    }

    public boolean check(String value, Action1<String> errorField) {
        boolean valid = checkFunction.call(value);
        if (valid) {
            errorField.call(null);
        } else {
            errorField.call(errorMessage.call());
        }
        return valid;
    }
}
