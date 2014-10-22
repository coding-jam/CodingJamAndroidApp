package it.cosenonjaviste.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import it.cosenonjaviste.R;

public class SingleFragmentActivity extends FragmentActivity {

    public static final String FRAGMENT_CLASS = "fragmentClass";

    public static Intent createIntent(Context context, Class<? extends Fragment> fragmentClass) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        populateIntent(intent, fragmentClass);
        return intent;
    }

    public static Intent createIntent(Class<? extends Fragment> fragmentClass) {
        Intent intent = new Intent();
        populateIntent(intent, fragmentClass);
        return intent;
    }

    private static Intent populateIntent(Intent intent, Class<? extends Fragment> fragmentClass) {
        intent.putExtra(FRAGMENT_CLASS, fragmentClass.getName());
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_fragment);
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
}
