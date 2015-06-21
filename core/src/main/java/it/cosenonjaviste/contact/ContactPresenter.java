package it.cosenonjaviste.contact;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableString;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import rx.Observable;
import rx.functions.Action0;

@PresenterScope
public class ContactPresenter extends RxMvpPresenter<ContactModel, ContactView> {

    @Inject public ContactPresenter() {
    }

    @Override public void resume() {
        super.resume();

        ContactView view = getView();
        ContactModel model = getModel();

        bind(this::send, view.onSend());

        bind(model.name, view.name());
        bind(model.subject, view.subject());
        bind(model.email, view.email());
        bind(model.message, view.message());

        bind(this::validate, view.name(), view.subject(), view.email(), view.message());
    }

    private void validate() {
        ContactModel model = getModel();
        if (model.sendPressed) {
            model.nameError.set(model.name.isEmpty() ? "Error" : null);
            model.emailError.set(model.email.isEmpty() ? "Error" : null);
            model.subjectError.set(model.subject.isEmpty() ? "Error" : null);
            model.messageError.set(model.message.isEmpty() ? "Error" : null);
        }
    }

    protected void bind(BindableString field, Observable<String> observable) {
        observable.subscribe(field::setFromView);
    }

    protected void bind(Action0 method, Observable<?>... observables) {
        for (int i = 0; i < observables.length; i++) {
            observables[i].subscribe(v -> method.call());
        }
    }

    private void send() {
        ContactModel model = getModel();
        model.sendPressed = true;
        validate();
        System.out.println(model.name.get() + " " + model.subject.get());
    }
}
