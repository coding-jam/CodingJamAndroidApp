package it.cosenonjaviste.core;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.ui.AppModule;
import it.cosenonjaviste.ui.ApplicationComponent;

@Singleton
@Component(modules = {AppModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
    Gson gson();
}
