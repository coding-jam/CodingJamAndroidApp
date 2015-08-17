package it.cosenonjaviste.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.ViewModelManager;

public class SingleFragmentActivity extends AppCompatActivity {

    private static final String VIEW_CLASS = "viewClass";

    public static Intent populateIntent(Intent intent, Class<?> viewClass) {
        intent.putExtra(VIEW_CLASS, viewClass.getName());
        return intent;
    }

    public static Intent createIntent(Context context, Class<?> viewClass) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        populateIntent(intent, viewClass);
        return intent;
    }

    public static Intent createIntent(Class<?> viewClass) {
        Intent intent = new Intent();
        populateIntent(intent, viewClass);
        return intent;
    }

    public static <MM extends Parcelable> void open(FragmentActivity activity, Class<?> viewClass, MM model) {
        Intent intent = createIntent(activity, viewClass);
        intent.putExtra(ViewModelManager.MODEL, model);
        activity.startActivity(intent);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_fragment);

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
