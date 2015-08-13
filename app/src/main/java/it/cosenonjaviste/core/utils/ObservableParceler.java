package it.cosenonjaviste.core.utils;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.parceler.Parcels;

public class ObservableParceler<T> extends ObservableField<T> implements Parcelable {

    public static final Creator<ObservableParceler> CREATOR = new Creator<ObservableParceler>() {
        public ObservableParceler createFromParcel(Parcel source) {
            return new ObservableParceler(Parcels.unwrap(source.readParcelable(this.getClass().getClassLoader())));
        }

        @NonNull public ObservableParceler[] newArray(int size) {
            return new ObservableParceler[size];
        }
    };

    public ObservableParceler(T value) {
        super(value);
    }

    public ObservableParceler() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(Parcels.wrap(this.get()), 0);
    }
}