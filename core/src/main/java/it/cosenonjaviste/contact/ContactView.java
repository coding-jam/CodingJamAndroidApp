package it.cosenonjaviste.contact;

import rx.Observable;

public interface ContactView {
    Observable<Void> onSend();

    Observable<String> name();

    Observable<String> subject();

    Observable<String> email();

    Observable<String> message();
}
