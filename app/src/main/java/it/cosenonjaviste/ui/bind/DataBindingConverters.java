package it.cosenonjaviste.ui.bind;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import java.util.Date;

import it.cosenonjaviste.R;
import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.bind.BindableString;
import it.cosenonjaviste.core.contact.BindableValidationError;
import it.cosenonjaviste.core.contact.ValidationError;
import it.cosenonjaviste.ui.utils.CircleTransform;
import it.cosenonjaviste.ui.utils.DateFormatter;
import it.cosenonjaviste.ui.utils.TextWatcherAdapter;

@ParcelClasses({@ParcelClass(BindableString.class)})
public class DataBindingConverters {

    private static CircleTransform circleTransformation;

    @BindingConversion
    public static String convertBindableToString(BindableString bindableString) {
        return bindableString.get();
    }

    @BindingConversion
    public static boolean convertBindableToBoolean(BindableBoolean bindableBoolean) {
        return bindableBoolean != null && bindableBoolean.get();
    }

    @BindingConversion
    public static CharSequence convertDateToCharSequence(Date date) {
        return DateFormatter.formatDate(date);
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
        view.setVisibility(bindableBoolean != null && bindableBoolean.get() ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter({"app:visibleOrGone"})
    public static void bindVisibleOrGone(View view, BindableBoolean bindableBoolean) {
        view.setVisibility(bindableBoolean != null && bindableBoolean.get() ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"app:visible"})
    public static void bindVisible(View view, boolean b) {
        view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (circleTransformation == null) {
                circleTransformation = CircleTransform.createWithBorder(view.getResources(), R.dimen.author_image_size_big, R.color.colorPrimary, R.dimen.author_image_border);
            }
            Picasso.with(view.getContext()).load(url).transform(circleTransformation).into(view);
        } else {
            view.setImageDrawable(null);
        }
    }

    @BindingAdapter({"app:textHtml"})
    public static void bindHtmlText(TextView view, String text) {
        view.setText(Html.fromHtml(text));
    }

    @BindingAdapter({"app:onClick"})
    public static void bindOnClick(View view, Runnable listener) {
        view.setOnClickListener(v -> listener.run());
    }

    @BindingAdapter({"bind:text1", "bind:textParam1"})
    public static void bindHtmlText(TextView view, int textRes, Object param) {
        view.setText(view.getResources().getString(textRes, param));
    }
}
