package it.cosenonjaviste.contact;

import org.parceler.Parcel;

import it.cosenonjaviste.bind.BaseObservable;

@Parcel
public class BindableValidationError extends BaseObservable {
    ValidationError value;

    public ValidationError get() {
        return value;
    }

    public void set(ValidationError value) {
        if (this.value != value) {
            this.value = value;
            notifyChange();
        }
    }
}