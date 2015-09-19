package it.cosenonjaviste.core;

import it.cosenonjaviste.mv2m.ActivityAware;

public interface MessageManager extends ActivityAware {
    void showMessage(int message);
}
