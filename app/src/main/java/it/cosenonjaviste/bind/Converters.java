package it.cosenonjaviste.bind;

import android.databinding.BindingConversion;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

@ParcelClasses({@ParcelClass(BindableString.class)})
public class Converters {
    @BindingConversion
    public static String convertObservableToString(BindableString observableString) {
        return observableString.get();
    }
}
