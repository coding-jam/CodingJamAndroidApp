package it.cosenonjaviste.core.page;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.model.Post;

@ParcelablePlease
public class PageModel implements Parcelable {

    Post post;

    public PageModel() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        PageModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PageModel> CREATOR = new Creator<PageModel>() {
        public PageModel createFromParcel(Parcel source) {
            PageModel target = new PageModel();
            PageModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PageModel[] newArray(int size) {
            return new PageModel[size];
        }
    };
}
