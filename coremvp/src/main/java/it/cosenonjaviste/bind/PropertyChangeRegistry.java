package it.cosenonjaviste.bind;

import android.databinding.CallbackRegistry;
import android.databinding.Observable;

public class PropertyChangeRegistry extends CallbackRegistry<Observable.OnPropertyChangedCallback, Observable, Void> {
    private static final NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void> NOTIFIER_CALLBACK =
            new NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void>() {
        public void onNotifyCallback(Observable.OnPropertyChangedCallback callback, Observable sender, int arg, Void notUsed) {
            callback.onPropertyChanged(sender, arg);
        }
    };

    public PropertyChangeRegistry() {
        super(NOTIFIER_CALLBACK);
    }

    public void notifyChange(Observable observable, int propertyId) {
        this.notifyCallbacks(observable, propertyId, null);
    }
}
