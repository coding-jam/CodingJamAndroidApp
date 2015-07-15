package it.cosenonjaviste.bind;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputLayout;
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
    public static String bindValidationError(TextInputLayout textInputLayout, BindableValidationError bindableValidationError) {
        Resources res = textInputLayout.getResources();
        ValidationError validationError = bindableValidationError.get();
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
}
