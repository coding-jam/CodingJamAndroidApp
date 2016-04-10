package it.cosenonjaviste.core;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.model.TwitterService;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.ui.AppModule;

public class CnjJUnitDaggerRule extends DaggerMockRule<TestApplicationComponent> {
    public CnjJUnitDaggerRule() {
        super(TestApplicationComponent.class, new AppModule(null));
        providesMock(WordPressService.class, TwitterService.class);
    }
}
