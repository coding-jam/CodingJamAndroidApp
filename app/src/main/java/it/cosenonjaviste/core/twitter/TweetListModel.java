package it.cosenonjaviste.core.twitter;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.lib.mvp.ListModel;
import it.cosenonjaviste.model.Tweet;

@ParcelablePlease
public class TweetListModel extends ListModel<Tweet> implements Parcelable {

    boolean moreDataAvailable;

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        TweetListModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TweetListModel> CREATOR = new Creator<TweetListModel>() {
        public TweetListModel createFromParcel(Parcel source) {
            TweetListModel target = new TweetListModel();
            TweetListModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TweetListModel[] newArray(int size) {
            return new TweetListModel[size];
        }
    };
}
