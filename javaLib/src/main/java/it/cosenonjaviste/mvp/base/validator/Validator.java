package it.cosenonjaviste.mvp.base.validator;

import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class Validator {

    private static final String EMAIL =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+";

    private boolean valid = true;

    public static FieldValidator check(Func1<String, Boolean> checkFunction, Func0<String> errorMessage) {
        return new FieldValidator(checkFunction, errorMessage);
    }

    public Validator validate(String value, Action1<String> errorField, FieldValidator... rules) {
        for (FieldValidator rule : rules) {
            boolean ruleValid = rule.check(value, errorField);
            if (!ruleValid) {
                valid = false;
                break;
            }
        }
        return this;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isValidEmail(String s) {
        return s.matches(EMAIL);
    }

    public boolean isValid() {
        return valid;
    }
}
