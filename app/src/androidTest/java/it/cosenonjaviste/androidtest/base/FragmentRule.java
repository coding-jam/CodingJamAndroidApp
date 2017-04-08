package it.cosenonjaviste.androidtest.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import it.cosenonjaviste.core.base.ViewModel;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

public class FragmentRule extends ActivityTestRule<SingleFragmentActivity> {
    private Class<? extends Fragment> fragmentClass;

    public FragmentRule(Class<? extends Fragment> fragmentClass) {
        super(SingleFragmentActivity.class, false, false);
        this.fragmentClass = fragmentClass;
    }

    public SingleFragmentActivity launchFragment() {
        return launchFragment(null);
    }

    public void launchFragment(Parcelable model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ViewModel.MODEL, model);
        launchFragment(bundle);
    }

    public SingleFragmentActivity launchFragment(Bundle b) {
        Intent intent = SingleFragmentActivity.populateIntent(new Intent(), fragmentClass);
        if (b != null) {
            intent.putExtras(b);
        }
        return launchActivity(intent);
    }

    public <T extends Context> T getApplication() {
        return (T) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}
