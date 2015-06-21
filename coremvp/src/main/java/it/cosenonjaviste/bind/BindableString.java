package it.cosenonjaviste.bind;

public class BindableString extends BaseObservable {
    private String mValue;

    public BindableString() {
    }

    public String get() {
        return this.mValue;
    }

    public void set(String value) {
        this.mValue = value;
        notifyChange();
    }

    public void setFromView(String value) {
        this.mValue = value;
    }

    public boolean isEmpty() {
        return mValue == null || mValue.isEmpty();
    }
}