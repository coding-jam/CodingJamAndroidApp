package it.cosenonjaviste.core.utils;

import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.parceler.Parcels;

import java.util.ArrayList;

public class ObservableParcelerArrayList<T> extends ObservableArrayList<T> implements Parcelable {
    public static final Creator<ObservableParcelerArrayList<?>> CREATOR = new Creator<ObservableParcelerArrayList<?>>() {
        public ObservableParcelerArrayList createFromParcel(Parcel source) {
            ObservableParcelerArrayList list = new ObservableParcelerArrayList();
            ArrayList<?> parceledList = Parcels.unwrap(source.readParcelable(getClass().getClassLoader()));
            list.addAll(parceledList);
            return list;
        }

        @NonNull public ObservableParcelerArrayList[] newArray(int size) {
            return new ObservableParcelerArrayList[size];
        }
    };


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(Parcels.wrap(this), 0);
    }
}
