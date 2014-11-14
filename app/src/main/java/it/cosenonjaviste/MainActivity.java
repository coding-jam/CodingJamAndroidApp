package it.cosenonjaviste;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.lib.mvp.ActivityContextBinder;
import it.cosenonjaviste.lib.mvp.parceler.OptionalItemConverter;
import it.cosenonjaviste.lib.mvp.parceler.OptionalListConverter;
import it.cosenonjaviste.mvp.author.AuthorListView;
import it.cosenonjaviste.mvp.base.PresenterArgs;
import it.cosenonjaviste.mvp.base.optional.OptionalItem;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.mvp.category.CategoryListView;
import it.cosenonjaviste.mvp.page.PagePresenter;
import it.cosenonjaviste.mvp.page.PageView;
import it.cosenonjaviste.mvp.post.PostListView;
import it.cosenonjaviste.mvp.twitter.TweetListView;
import it.cosenonjaviste.utils.ComponentBuilder;

@ParcelClasses({
        @ParcelClass(value = OptionalItem.class, converter = OptionalItemConverter.class),
        @ParcelClass(value = OptionalList.class, converter = OptionalListConverter.class)
})
public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer_menu) View mDrawerMenu;
    @InjectView(R.id.left_drawer) ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Inject CnjPresenterConfig cnjPresenterConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponentBuilder.build(AppComponent.class, new AppModule(getApplication())).inject(this);
        //TODO move
        cnjPresenterConfig.init();
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] menuItems = getResources().getStringArray(R.array.menu_items);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, menuItems));
        mDrawerList.setOnItemClickListener((parent, view, position, id) -> selectItem(position));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int position) {
        String tag = "fragment_" + position;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            fragment = createFragment(position);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerMenu);
    }

    private Fragment createFragment(int position) {
        //TODO activity title
        ActivityContextBinder contextBinder = new ActivityContextBinder(this);
        switch (position) {
            case 1:
                return contextBinder.createView(CategoryListView.class, null);
            case 2:
                return contextBinder.createView(AuthorListView.class, null);
            case 3:
                return contextBinder.createView(TweetListView.class, null);
            case 4:
                PresenterArgs args = PagePresenter.populateArgs(contextBinder.createArgs(), "http://www.cosenonjaviste.it/contatti/");
                return contextBinder.createView(PageView.class, args);
            default:
                return contextBinder.createView(PostListView.class, null);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}