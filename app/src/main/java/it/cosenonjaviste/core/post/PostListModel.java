package it.cosenonjaviste.core.post;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.core.list.ListModel;
import it.cosenonjaviste.model.Post;

@ParcelablePlease
public class PostListModel extends ListModel<Post> implements Parcelable {

    boolean moreDataAvailable;

    public PostListModel() {
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public int size() {
        return items.size();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        PostListModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PostListModel> CREATOR = new Creator<PostListModel>() {
        public PostListModel createFromParcel(Parcel source) {
            PostListModel target = new PostListModel();
            PostListModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PostListModel[] newArray(int size) {
            return new PostListModel[size];
        }
    };
}
