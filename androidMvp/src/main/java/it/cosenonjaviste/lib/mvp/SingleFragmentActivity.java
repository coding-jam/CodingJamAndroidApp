package it.cosenonjaviste.lib.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class SingleFragmentActivity extends ActionBarActivity {

    public static final String FRAGMENT_CLASS = "fragmentClass";

    public static Intent createIntent(Context context, String fragmentClassName) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        intent.putExtra(FRAGMENT_CLASS, fragmentClassName);
        return intent;
    }

    public static Intent createIntent(Context context, Class<? extends Fragment> fragmentClass) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        intent.putExtra(FRAGMENT_CLASS, fragmentClass.getName());
        return intent;
    }

    public static Intent createIntent(Class<? extends Fragment> fragmentClass) {
        Intent intent = new Intent();
        intent.putExtra(FRAGMENT_CLASS, fragmentClass.getName());
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_fragment);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String fragmentClass = getIntent().getStringExtra(FRAGMENT_CLASS);
            Fragment fragment = Fragment.instantiate(this, fragmentClass);
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
