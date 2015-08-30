package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Attachment implements Parcelable {
    String url;

    public Attachment() {
    }

    public Attachment(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        AttachmentParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        public Attachment createFromParcel(Parcel source) {
            Attachment target = new Attachment();
            AttachmentParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}
