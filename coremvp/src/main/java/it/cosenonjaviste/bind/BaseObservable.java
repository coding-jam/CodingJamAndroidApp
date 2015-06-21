package it.cosenonjaviste.bind;

import android.databinding.Observable;

import org.parceler.Transient;

public class BaseObservable implements Observable {
    @Transient
    private PropertyChangeRegistry mCallbacks;

    public BaseObservable() {
    }

    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
        if(this.mCallbacks == null) {
            this.mCallbacks = new PropertyChangeRegistry();
        }

        this.mCallbacks.add(listener);
    }

    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
        if(this.mCallbacks != null) {
            this.mCallbacks.remove(listener);
        }

    }

    public synchronized void notifyChange() {
        if(this.mCallbacks != null) {
            this.mCallbacks.notifyCallbacks(this, 0, (Void)null);
        }

    }

    public void notifyPropertyChanged(int fieldId) {
        if(this.mCallbacks != null) {
            this.mCallbacks.notifyCallbacks(this, fieldId, (Void)null);
        }

    }
}
