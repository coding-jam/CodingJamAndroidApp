package it.cosenonjaviste.contact;

import it.cosenonjaviste.bind.BindableString;

public class ContactModel {

    public boolean sendPressed;

    public final BindableString name = new BindableString();
    public final BindableString email = new BindableString();
    public final BindableString subject = new BindableString();
    public final BindableString message = new BindableString();

    public final BindableString nameError = new BindableString();
    public final BindableString emailError = new BindableString();
    public final BindableString subjectError = new BindableString();
    public final BindableString messageError = new BindableString();
}
