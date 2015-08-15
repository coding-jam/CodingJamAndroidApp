package it.cosenonjaviste.core.author;

import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.core.utils.ObservableArrayListBagger;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Author;

@ParcelablePlease
public class AuthorListModel extends ListModelAdapter<Author> implements Parcelable {

    @Bagger(ObservableArrayListBagger.class)
    ObservableArrayList<Author> items = new ObservableArrayList<>();

    public int size() {
        return items.size();
    }

    public Author get(int index) {
        return items.get(index);
    }

    public ObservableArrayList<Author> getItems() {
        return items;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        AuthorListModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<AuthorListModel> CREATOR = new Creator<AuthorListModel>() {
        public AuthorListModel createFromParcel(Parcel source) {
            AuthorListModel target = new AuthorListModel();
            AuthorListModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public AuthorListModel[] newArray(int size) {
            return new AuthorListModel[size];
        }
    };
}
