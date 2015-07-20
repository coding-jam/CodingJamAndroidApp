package it.cosenonjaviste.bind;

import android.databinding.Observable;

import org.parceler.Parcel;

@Parcel
public class BindableBoolean extends BaseObservable {
    boolean value;

    public BindableBoolean() {
    }

    public BindableBoolean(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            notifyChange();
        }
    }

    public BindableBoolean not() {
        final BindableBoolean not = new BindableBoolean(!value);
        addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable sender, int propertyId) {
                not.set(!BindableBoolean.this.value);
            }
        });
        return not;
    }

    public BindableBoolean and(final BindableBoolean other) {
        final BindableBoolean ret = new BindableBoolean(value && other.value);
        OnPropertyChangedCallback listener = new OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable sender, int propertyId) {
                ret.set(value && other.value);
            }
        };
        addOnPropertyChangedCallback(listener);
        other.addOnPropertyChangedCallback(listener);
        return ret;
    }
}