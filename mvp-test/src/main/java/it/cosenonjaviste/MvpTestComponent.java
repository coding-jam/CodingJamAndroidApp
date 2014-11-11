package it.cosenonjaviste;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MvpTestModule.class })
public interface MvpTestComponent {
}
