package it.cosenonjaviste.bind;

import android.databinding.Observable;

import org.parceler.Parcel;

import rx.functions.Action1;

@Parcel
public class BindableString extends BaseObservable {
    String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    @Override public String toString() {
        return value;
    }


    public void addListener(Action1<String> listener) {
        addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override public void onPropertyChanged(Observable sender, int propertyId) {
                listener.call(((BindableString) sender).get());
            }
        });
    }
}