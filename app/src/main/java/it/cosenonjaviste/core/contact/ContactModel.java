package it.cosenonjaviste.core.contact;

import android.databinding.ObservableInt;

import it.cosenonjaviste.core.utils.ObservableString;

public class ContactModel {

    public boolean sendPressed;

    public ObservableString name = new ObservableString();
    public ObservableString email = new ObservableString();
    public ObservableString message = new ObservableString();

    public ObservableInt nameError = new ObservableInt();
    public ObservableInt emailError = new ObservableInt();
    public ObservableInt messageError = new ObservableInt();
}
