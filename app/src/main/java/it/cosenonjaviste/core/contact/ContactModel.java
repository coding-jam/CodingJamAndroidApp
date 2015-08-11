package it.cosenonjaviste.core.contact;

import it.cosenonjaviste.bind.BindableString;

public class ContactModel {

    public boolean sendPressed;

    public BindableString name = new BindableString();
    public BindableString email = new BindableString();
    public BindableString message = new BindableString();

    public BindableValidationError nameError = new BindableValidationError();
    public BindableValidationError emailError = new BindableValidationError();
    public BindableValidationError messageError = new BindableValidationError();
}
