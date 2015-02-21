package it.cosenonjaviste.androidtest;

import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.DaggerRule;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.model.WordPressService;

import static org.mockito.Mockito.when;

public class CategoryListTest {

    @Inject WordPressService wordPressService;

    private final FragmentRule fragmentRule = FragmentRule.create(AuthorListFragment.class, new AuthorListModel());

    private final DaggerRule daggerRule = new DaggerRule(component -> {
        component.inject(this);
        when(wordPressService.listCategories())
                .thenReturn(TestData.categoryResponse(3));
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(fragmentRule);

    public void testCategoryList() throws InterruptedException {
    }
}
