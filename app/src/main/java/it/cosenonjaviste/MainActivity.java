package it.cosenonjaviste;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.author.AuthorListFragment;
import it.cosenonjaviste.author.AuthorListModel;
import it.cosenonjaviste.category.CategoryListFragment;
import it.cosenonjaviste.category.CategoryListModel;
import it.cosenonjaviste.contact.ContactFragment;
import it.cosenonjaviste.lib.mvp.RxMvpPresenter;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;
import it.cosenonjaviste.twitter.TweetListFragment;
import it.cosenonjaviste.twitter.TweetListModel;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer_menu) NavigationView drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CoseNonJavisteApp) getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerMenu.setNavigationItemSelectedListener(menuItem -> {
            selectItem(menuItem.getItemId());
            menuItem.setChecked(true);
            return true;
        });
        Menu drawerMenu = this.drawerMenu.getMenu();
        drawerMenu.findItem(R.id.drawer_post).setIcon(new IconDrawable(this, IconValue.fa_home).actionBarSize());
        drawerMenu.findItem(R.id.drawer_authors).setIcon(new IconDrawable(this, IconValue.fa_user).actionBarSize());
        drawerMenu.findItem(R.id.drawer_categories).setIcon(new IconDrawable(this, IconValue.fa_tags).actionBarSize());
        drawerMenu.findItem(R.id.drawer_twitter).setIcon(new IconDrawable(this, IconValue.fa_twitter).actionBarSize());
        drawerMenu.findItem(R.id.drawer_contacts).setIcon(new IconDrawable(this, IconValue.fa_envelope).actionBarSize());

        if (savedInstanceState == null) {
            selectItem(R.id.drawer_post);
            drawerMenu.findItem(R.id.drawer_post).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int menuItemId) {
        String tag = "fragment_" + menuItemId;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            fragment = createFragment(menuItemId);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).commit();

        drawerLayout.closeDrawer(drawerMenu);
    }

    private Fragment createFragment(int menuItemId) {
        switch (menuItemId) {
            case R.id.drawer_categories:
                return createView(this, CategoryListFragment.class, new CategoryListModel());
            case R.id.drawer_authors:
                return createView(this, AuthorListFragment.class, new AuthorListModel());
            case R.id.drawer_twitter:
                return createView(this, TweetListFragment.class, new TweetListModel());
            case R.id.drawer_contacts:
                return createView(this, ContactFragment.class, null);
            default:
                return createView(this, PostListFragment.class, new PostListModel());
        }
    }

    public static <T, M> T createView(@NonNull Context context, @NonNull Class<?> viewClass, @Nullable M model) {
        Fragment fragment = Fragment.instantiate(context, viewClass.getName());
        if (model != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(RxMvpPresenter.MODEL, Parcels.wrap(model));
            fragment.setArguments(bundle);
        }
        return (T) fragment;
    }
}