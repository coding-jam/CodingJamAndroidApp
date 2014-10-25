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

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.lib.mvp.MultiFragmentActivity;
import it.cosenonjaviste.mvp.LoadableModel;
import it.cosenonjaviste.post.PostFragment2;

@ParcelClasses({@ParcelClass(LoadableModel.class)})
public class MainActivity extends ActionBarActivity implements MultiFragmentActivity {
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.left_drawer_menu) View mDrawerMenu;
    @InjectView(R.id.left_drawer) ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override public void showFragment(Fragment fragment) {
        replaceFragmentInContainer(fragment);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PostFragment2();

        replaceFragmentInContainer(fragment);

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerMenu);
    }

    private void replaceFragmentInContainer(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
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