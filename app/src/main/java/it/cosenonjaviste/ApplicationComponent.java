package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.lib.mvp.utils.SchedulerManager;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.page.PageUrlManager;

@Singleton
@Component(modules = {AppModule.class})
public interface ApplicationComponent {

    void inject(MainActivity activity);

    SchedulerManager providesSchedulerManager();

    WordPressService providesWordPressService();

    PageUrlManager providesPageUrlManager();

    TwitterService providesTwitterService();
}
