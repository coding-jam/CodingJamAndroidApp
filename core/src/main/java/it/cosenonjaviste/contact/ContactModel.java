package it.cosenonjaviste.contact;

import org.parceler.Transient;

import it.cosenonjaviste.bind.BindableBoolean;
import it.cosenonjaviste.bind.BindableString;

public class ContactModel {

    public boolean sendPressed;

    @Transient
    public BindableBoolean sending = new BindableBoolean();

    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    public BindableString message = new BindableString();

    public BindableValidationError nameError = new BindableValidationError();
    public BindableValidationError emailError = new BindableValidationError();
    public BindableValidationError messageError = new BindableValidationError();
}
