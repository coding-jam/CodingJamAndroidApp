package it.cosenonjaviste.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.MvpView;

public class SingleFragmentActivity extends ActionBarActivity {

    private static final String VIEW_CLASS = "viewClass";

    public static Intent populateIntent(Intent intent, Class<? extends MvpView<?>> viewClass) {
        intent.putExtra(VIEW_CLASS, viewClass.getName());
        return intent;
    }

    public static Intent createIntent(Context context, Class<? extends MvpView<?>> viewClass) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        populateIntent(intent, viewClass);
        return intent;
    }

    public static Intent createIntent(Class<? extends MvpView<?>> viewClass) {
        Intent intent = new Intent();
        populateIntent(intent, viewClass);
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_fragment);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String viewClassName = getIntent().getStringExtra(VIEW_CLASS);
            Fragment fragment = Fragment.instantiate(this, viewClassName);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Bundle b = new Bundle(extras);
                fragment.setArguments(b);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.single_fragment_root, fragment).commit();
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
