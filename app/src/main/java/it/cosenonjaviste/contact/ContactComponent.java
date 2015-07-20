package it.cosenonjaviste.contact;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.PresenterScope;

@PresenterScope
@Component(dependencies = ApplicationComponent.class)
public interface ContactComponent {
    void inject(ContactFragment fragment);
}
