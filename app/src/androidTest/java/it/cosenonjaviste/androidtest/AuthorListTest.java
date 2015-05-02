package it.cosenonjaviste.androidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.DaggerTestComponent;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.base.TestComponent;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.model.WordPressService;

import static org.mockito.Mockito.when;

public class AuthorListTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(AuthorListFragment.class);

    @Before
    public void setUp() {
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestComponent component = DaggerTestComponent.builder().build();
        ((CoseNonJavisteApp) app).setComponent(component);

        component.inject(this);

        when(wordPressService.listAuthors())
                .thenReturn(TestData.authorResponse(2));
    }

    @Test
    public void testAuthorList() {

        fragmentRule.launchFragment(new AuthorListModel());
    }
}
