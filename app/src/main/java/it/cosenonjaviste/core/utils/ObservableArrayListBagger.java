package it.cosenonjaviste.core.utils;

import android.databinding.ObservableArrayList;
import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

public class ObservableArrayListBagger implements ParcelBagger<ObservableArrayList> {
    @Override public void write(ObservableArrayList value, Parcel out, int flags) {
        out.writeList(value);
    }

    @Override public ObservableArrayList read(Parcel in) {
        ObservableArrayList observableArrayList = new ObservableArrayList();
        in.readList(observableArrayList, observableArrayList.getClass().getClassLoader());
        return observableArrayList;
    }
}
