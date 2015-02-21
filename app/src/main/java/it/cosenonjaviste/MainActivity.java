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

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.page.PageModel;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer_menu) View mDrawerMenu;
    @InjectView(R.id.left_drawer) ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CoseNonJavisteApp) getApplication()).getComponent().inject(this);

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
        switch (position) {
            case 1:
                return CnjFragment.createView(this, CategoryListFragment.class, new CategoryListModel());
            case 2:
                return CnjFragment.createView(this, AuthorListFragment.class, new AuthorListModel());
            case 3:
                return CnjFragment.createView(this, TweetListFragment.class, new TweetListModel());
            case 4:
                return CnjFragment.createView(this, PageFragment.class, new PageModel("http://www.cosenonjaviste.it/contatti/"));
            default:
                return CnjFragment.createView(this, PostListFragment.class, new PostListModel());
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