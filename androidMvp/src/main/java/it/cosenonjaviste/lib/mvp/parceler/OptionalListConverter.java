package it.cosenonjaviste.lib.mvp.parceler;

import android.os.Parcel;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.List;

import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class OptionalListConverter implements ParcelConverter<OptionalList> {
    @Override public void toParcel(OptionalList input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input.getObject()), 0);
        parcel.writeSerializable(input.getThrowable());
    }

    @Override public OptionalList fromParcel(Parcel parcel) {
        List<?> value = Parcels.unwrap(parcel.readParcelable(OptionalListConverter.class.getClassLoader()));
        Throwable t = (Throwable) parcel.readSerializable();
        return new OptionalList<>(value, t);
    }
}
