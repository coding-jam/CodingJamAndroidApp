package it.cosenonjaviste.androidtest.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment

import it.cosenonjaviste.mv2m.ViewModel
import it.cosenonjaviste.ui.utils.SingleFragmentActivity

class FragmentRule(private val fragmentClass: Class<out Fragment>) : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, false, false) {

    fun launchFragment(model: Parcelable) {
        val bundle = Bundle()
        bundle.putParcelable(ViewModel.MODEL, model)
        launchFragment(bundle as Bundle?)
    }

    @JvmOverloads fun launchFragment(b: Bundle? = null): SingleFragmentActivity {
        val intent = SingleFragmentActivity.populateIntent(Intent(), fragmentClass)
        if (b != null) {
            intent.putExtras(b)
        }
        return launchActivity(intent)
    }
}
