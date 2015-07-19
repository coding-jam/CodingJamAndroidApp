package it.cosenonjaviste.contact;

import javax.inject.Inject;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.bind.BindableString;
import it.cosenonjaviste.lib.mvp.PresenterScope;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.utils.EmailVerifier;
import retrofit.client.Response;
import rx.Observable;

@PresenterScope
public class ContactPresenter extends RxMvpPresenter<ContactModel, ContactView> {

    @Inject MailJetService mailJetService;

    public BindableBoolean sending = new BindableBoolean();

    @Inject public ContactPresenter() {
    }

    @Override public ContactModel createDefaultModel() {
        return new ContactModel();
    }

    @Override public void resume() {
        super.resume();

        getModel().name.addListener(s -> validate());
        getModel().message.addListener(s -> validate());
        getModel().email.addListener(s -> validate());
    }

    private boolean validate() {
        ContactModel model = getModel();
        if (model.sendPressed) {
            boolean isValid = checkMandatory(model.name, model.nameError);
            if (!model.email.isEmpty()) {
                if (!EmailVerifier.checkEmail(model.email.get())) {
                    model.emailError.set(ValidationError.INVALID_EMAIL);
                    isValid = false;
                } else {
                    model.emailError.set(null);
                }
            } else {
                model.emailError.set(ValidationError.MANDATORY_FIELD);
                isValid = false;
            }
            isValid = checkMandatory(model.message, model.messageError) && isValid;
            return isValid;
        } else {
            return true;
        }
    }

    private boolean checkMandatory(BindableString bindableString, BindableValidationError error) {
        boolean empty = bindableString.isEmpty();
        error.set(empty ? ValidationError.MANDATORY_FIELD : null);
        return !empty;
    }

    public void send() {
        ContactModel model = getModel();
        model.sendPressed = true;
        if (validate()) {
            sending.set(true);

            Observable<Response> observable = mailJetService.sendEmail(
                    model.name + " <info@cosenonjaviste.it>",
                    "info@cosenonjaviste.it",
                    "Email from " + model.name,
                    "Reply to: " + model.email + "\n" + model.message
            ).finallyDo(() -> sending.set(false));

            subscribe(
                    observable,
                    r -> getView().showSentMessage(),
                    t -> getView().showSentError()
            );
        }
    }

    public BindableString getName() {
        return getModel().name;
    }

    public BindableString getEmail() {
        return getModel().email;
    }

    public BindableString getMessage() {
        return getModel().message;
    }

    public BindableValidationError getNameError() {
        return getModel().nameError;
    }

    public BindableValidationError getEmailError() {
        return getModel().emailError;
    }

    public BindableValidationError getMessageError() {
        return getModel().messageError;
    }
}
