package it.cosenonjaviste.twitter;

import dagger.Component;
import it.cosenonjaviste.ApplicationComponent;
import it.cosenonjaviste.utils.PresenterScope;

@PresenterScope
@Component(dependencies = ApplicationComponent.class)
public interface TweetListComponent {
    void inject(TweetListFragment fragment);
}
