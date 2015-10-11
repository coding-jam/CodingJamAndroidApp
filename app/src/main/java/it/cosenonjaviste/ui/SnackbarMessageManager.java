package it.cosenonjaviste.ui;

import android.app.Activity;
import android.support.design.widget.Snackbar;

import it.cosenonjaviste.core.MessageManager;

public class SnackbarMessageManager implements MessageManager {
    private Activity activity;

    @Override public void showMessage(Activity activity, int message) {
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }
}
