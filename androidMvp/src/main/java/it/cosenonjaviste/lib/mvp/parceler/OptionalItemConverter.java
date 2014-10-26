package it.cosenonjaviste.lib.mvp.parceler;

import android.os.Parcel;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.optional.OptionalItem;

public class OptionalItemConverter implements ParcelConverter<OptionalItem> {
    @Override public void toParcel(OptionalItem input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input.getObject()), 0);
        parcel.writeSerializable(input.getThrowable());
    }

    @Override public OptionalItem fromParcel(Parcel parcel) {
        Object value = Parcels.unwrap(parcel.readParcelable(OptionalItemConverter.class.getClassLoader()));
        Throwable t = (Throwable) parcel.readSerializable();
        return new OptionalItem<>(value, t);
    }
}
