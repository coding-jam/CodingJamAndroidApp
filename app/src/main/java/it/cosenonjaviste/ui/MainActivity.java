package it.cosenonjaviste.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.ActivityMainBinding;
import it.cosenonjaviste.ui.author.AuthorListFragment;
import it.cosenonjaviste.ui.category.CategoryListFragment;
import it.cosenonjaviste.ui.contact.ContactFragment;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.twitter.TweetListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CoseNonJavisteApp) getApplication()).getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        binding.drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        binding.leftDrawerMenu.setNavigationItemSelectedListener(menuItem -> {
            selectItem(menuItem.getItemId());
            menuItem.setChecked(true);
            return true;
        });

        if (savedInstanceState == null) {
            selectItem(R.id.drawer_post);
            binding.leftDrawerMenu.getMenu().findItem(R.id.drawer_post).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
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

        binding.drawerLayout.closeDrawer(binding.leftDrawerMenu);
    }

    private Fragment createFragment(int menuItemId) {
        switch (menuItemId) {
            case R.id.drawer_categories:
                return Fragment.instantiate(this, CategoryListFragment.class.getName());
            case R.id.drawer_authors:
                return Fragment.instantiate(this, AuthorListFragment.class.getName());
            case R.id.drawer_twitter:
                return Fragment.instantiate(this, TweetListFragment.class.getName());
            case R.id.drawer_contacts:
                return Fragment.instantiate(this, ContactFragment.class.getName());
            default:
                return Fragment.instantiate(this, PostListFragment.class.getName());
        }
    }
}