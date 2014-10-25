package it.cosenonjaviste.lib.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import it.cosenonjaviste.lib.mvp.dagger.DaggerApplication;
import it.cosenonjaviste.lib.mvp.dagger.ObjectGraphHolder;
import it.cosenonjaviste.mvp.base.RxMvpPresenter;
import it.cosenonjaviste.mvp.base.RxMvpView;

public class SingleFragmentActivity extends ActionBarActivity {

    public static final String VIEW_CLASS = "viewClass";
    public static final String PRESENTER_CLASS = "presenterClass";

//    public static Intent createIntent(Context context, String fragmentClassName) {
//        Intent intent = new Intent(context, SingleFragmentActivity.class);
//        intent.putExtra(VIEW_CLASS, fragmentClassName);
//        return intent;
//    }
//
    public static <M> Intent createIntent(Context context, Class<? extends RxMvpView<M>> viewClass, Class<? extends RxMvpPresenter<M>> presenterClass) {
        Intent intent = new Intent(context, SingleFragmentActivity.class);
        intent.putExtra(VIEW_CLASS, viewClass.getName());
        intent.putExtra(PRESENTER_CLASS, presenterClass.getName());
        return intent;
    }

//    public static Intent createIntent(Class<? extends Fragment> fragmentClass) {
//        Intent intent = new Intent();
//        intent.putExtra(VIEW_CLASS, fragmentClass.getName());
//        return intent;
//    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_fragment);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String viewClass = getIntent().getStringExtra(VIEW_CLASS);
            Fragment fragment = (Fragment) ObjectGraphHolder.getObjectGraph((DaggerApplication) getApplication()).get(getType(viewClass));
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Bundle b = new Bundle(extras);
                fragment.setArguments(b);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.single_fragment_root, fragment).commit();
        }
    }

    private Class<?> getType(String viewClass) {
        try {
            return Class.forName(viewClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
