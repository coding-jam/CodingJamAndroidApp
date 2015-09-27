package it.cosenonjaviste.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import it.cosenonjaviste.R;
import it.cosenonjaviste.mv2m.ArgumentManager;
import it.cosenonjaviste.mv2m.ViewModel;
import it.cosenonjaviste.mv2m.ViewModelFragment;

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

    public static <ARG, VM extends ViewModel<ARG, ?>, F extends ViewModelFragment<VM>> void open(FragmentActivity activity, Class<F> viewClass, ARG arg) {
        Intent intent = createIntent(activity, viewClass);
        ArgumentManager.writeArgument(intent, arg);
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
