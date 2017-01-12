package it.cosenonjaviste.core.contact;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import it.codingjam.lifecyclebinder.BindLifeCycle;
import it.cosenonjaviste.R;
import it.cosenonjaviste.core.Navigator;
import it.cosenonjaviste.core.utils.EmailVerifier;
import it.cosenonjaviste.model.MailJetService;
import it.cosenonjaviste.mv2m.rx.RxViewModel;
import retrofit.client.Response;
import rx.Observable;

public class ContactViewModel extends RxViewModel<Void, ContactModel> {

    @Inject MailJetService mailJetService;

    @Inject @BindLifeCycle Navigator navigator;

    public final ObservableBoolean sending = new ObservableBoolean();

    private OnPropertyChangedCallback listener = new OnPropertyChangedCallback() {
        @Override public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            validate();
        }
    };

    @Inject public ContactViewModel() {
    }

    @NonNull @Override protected ContactModel createModel() {
        return new ContactModel();
    }

    @Override public void resume() {
        super.resume();

        model.name.addOnPropertyChangedCallback(listener);
        model.message.addOnPropertyChangedCallback(listener);
        model.email.addOnPropertyChangedCallback(listener);
    }

    @Override public void pause() {
        super.pause();
        model.name.removeOnPropertyChangedCallback(listener);
        model.message.removeOnPropertyChangedCallback(listener);
        model.email.removeOnPropertyChangedCallback(listener);
    }

    private boolean validate() {
        if (model.sendPressed) {
            boolean isValid = checkMandatory(model.nameError, model.name.get() == null || model.name.get().isEmpty());
            if (model.email.get() != null && !model.email.get().isEmpty()) {
                if (!EmailVerifier.checkEmail(model.email.get())) {
                    model.emailError.set(R.string.invalid_email);
                    isValid = false;
                } else {
                    model.emailError.set(0);
                }
            } else {
                model.emailError.set(R.string.mandatory_field);
                isValid = false;
            }
            isValid = checkMandatory(model.messageError, model.message.get() == null || model.message.get().isEmpty()) && isValid;
            return isValid;
        } else {
            return true;
        }
    }

    private boolean checkMandatory(ObservableInt error, boolean empty) {
        error.set(empty ? R.string.mandatory_field : 0);
        return !empty;
    }

    public void send() {
        model.sendPressed = true;
        if (validate()) {
            Observable<Response> observable = mailJetService.sendEmail(
                    model.name + " <info@cosenonjaviste.it>",
                    "info@cosenonjaviste.it",
                    "Email from " + model.name,
                    "Reply to: " + model.email + "\n" + model.message
            );

            subscribe(
                    sending::set,
                    observable,
                    r ->  navigator.showMessage(R.string.message_sent),
                    t -> navigator.showMessage(R.string.error_sending_message)
            );
        }
    }
}
