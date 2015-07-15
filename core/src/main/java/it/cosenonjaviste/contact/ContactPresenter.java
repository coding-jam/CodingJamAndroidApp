package it.cosenonjaviste.contact;

import com.annimon.stream.Stream;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableString;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.utils.EmailVerifier;
import retrofit.client.Response;
import rx.Observable;
import rx.functions.Action0;

@PresenterScope
public class ContactPresenter extends RxMvpPresenter<ContactModel, ContactView> {

    @Inject MailJetService mailJetService;

    @Inject public ContactPresenter() {
    }

    @Override public ContactModel createDefaultModel() {
        return new ContactModel();
    }

    @Override public void resume() {
        super.resume();

        ContactView view = getView();

        bind(this::send, view.onSend());
        bind(this::validate, view.name(), view.email(), view.message());
    }

    private boolean validate() {
        ContactModel model = getModel();
        boolean isValid = true;
        if (model.sendPressed) {
            isValid = checkMandatory(model.name, model.nameError);
            if (checkMandatory(model.email, model.emailError)) {
                if (!EmailVerifier.checkEmail(model.email.get())) {
                    model.emailError.set(ValidationError.INVALID_EMAIL);
                    isValid = false;
                }
            }
            isValid = checkMandatory(model.message, model.messageError) && isValid;
        }
        return isValid;
    }

    private boolean checkMandatory(BindableString bindableString, BindableValidationError error) {
        boolean empty = bindableString.isEmpty();
        error.set(empty ? ValidationError.MANDATORY_FIELD : null);
        return !empty;
    }

    protected void bind(Action0 method, Observable<?>... observables) {
        Stream.of(observables).forEach(o -> o.subscribe(v -> method.call()));
    }

    private void send() {
        ContactModel model = getModel();
        model.sendPressed = true;
        if (validate()) {
            Observable<Response> observable = mailJetService.sendEmail(
                    model.name + " <info@cosenonjaviste.it>",
                    "info@cosenonjaviste.it",
                    "Email from " + model.name,
                    "Reply to: " + model.email + "\n" + model.message);

            subscribe(
                    observable,
                    () -> getView().startSending(),
                    r -> getView().showSentMessage(),
                    t -> getView().showSentError()
            );
        }
    }
}
