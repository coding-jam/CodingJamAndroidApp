package it.cosenonjaviste.bind;

public class BindableString extends BaseObservable {
    private String mValue;

    public BindableString() {
    }

    public String get() {
        return this.mValue;
    }

    public void set(String value) {
        if (!equals(mValue, value)) {
            this.mValue = value;
            notifyChange();
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public void setFromView(String value) {
        this.mValue = value;
    }

    public boolean isEmpty() {
        return mValue == null || mValue.isEmpty();
    }
}