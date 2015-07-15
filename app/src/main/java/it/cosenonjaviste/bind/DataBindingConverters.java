package it.cosenonjaviste.bind;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import it.cosenonjaviste.R;
import it.cosenonjaviste.contact.BindableValidationError;
import it.cosenonjaviste.contact.ValidationError;
import it.cosenonjaviste.utils.TextWatcherAdapter;

@ParcelClasses({@ParcelClass(BindableString.class)})
public class DataBindingConverters {
    @BindingConversion
    public static String convertBindableToString(BindableString bindableString) {
        return bindableString.get();
    }

    @BindingConversion
    public static boolean convertBindableToBoolean(BindableBoolean bindableBoolean) {
        return bindableBoolean.get();
    }

    @BindingAdapter({"app:errorMessage"})
    public static void bindValidationError(TextInputLayout textInputLayout, BindableValidationError bindableValidationError) {
        String errorMessage = getValidationErrorString(textInputLayout.getResources(), bindableValidationError.get());
        textInputLayout.setError(errorMessage);
    }

    private static String getValidationErrorString(Resources res, ValidationError validationError) {
        if (validationError != null) {
            switch (validationError) {
                case MANDATORY_FIELD:
                    return res.getString(R.string.mandatory_field);
                case INVALID_EMAIL:
                    return res.getString(R.string.invalid_email);
            }
        }
        return null;
    }

    @BindingAdapter({"app:binding"})
    public static void bindEditText(EditText view, final BindableString bindableString) {
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.addTextChangedListener(new TextWatcherAdapter() {
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.set(s.toString());
                }
            });
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"app:visible"})
    public static void bindVisible(View view, BindableBoolean bindableBoolean) {
        view.setVisibility(bindableBoolean.get() ? View.VISIBLE : View.INVISIBLE);
    }
}
