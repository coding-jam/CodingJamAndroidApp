package it.cosenonjaviste.core.contact;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class ContactModel implements Parcelable {

    public boolean sendPressed;

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();

    public ObservableInt nameError = new ObservableInt();
    public ObservableInt emailError = new ObservableInt();
    public ObservableInt messageError = new ObservableInt();

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        ContactModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        public ContactModel createFromParcel(Parcel source) {
            ContactModel target = new ContactModel();
            ContactModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
