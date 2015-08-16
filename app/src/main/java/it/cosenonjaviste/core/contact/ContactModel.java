package it.cosenonjaviste.core.contact;

import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.core.utils.ObservableString;

@ParcelablePlease
public class ContactModel implements Parcelable {

    public boolean sendPressed;

    public ObservableString name = new ObservableString();
    public ObservableString email = new ObservableString();
    public ObservableString message = new ObservableString();

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
