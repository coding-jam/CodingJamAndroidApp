package it.cosenonjaviste.author;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.utils.PresenterScope;

@PresenterScope
@Component(dependencies = ApplicationComponent.class)
public interface AuthorListComponent {
    void inject(AuthorListFragment fragment);
}
