package it.cosenonjaviste.androidtest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.DaggerRule;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.model.WordPressService;

import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AuthorListTest {

    @Inject WordPressService wordPressService;

    private final FragmentRule fragmentRule = FragmentRule.create(AuthorListFragment.class, new AuthorListModel());

    private final DaggerRule daggerRule = new DaggerRule(component -> {
        component.inject(this);
        when(wordPressService.listAuthors())
                .thenReturn(TestData.authorResponse(2));
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(fragmentRule);

    @Test
    public void testAuthorList() {
//        showUi();
    }
}
