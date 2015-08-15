package it.cosenonjaviste.core.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Category;

@ParcelablePlease
public class CategoryListModel extends ListModelAdapter<Category> implements Parcelable {

    public Category get(int index) {
        return items.get(index);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        CategoryListModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<CategoryListModel> CREATOR = new Creator<CategoryListModel>() {
        public CategoryListModel createFromParcel(Parcel source) {
            CategoryListModel target = new CategoryListModel();
            CategoryListModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public CategoryListModel[] newArray(int size) {
            return new CategoryListModel[size];
        }
    };
}
