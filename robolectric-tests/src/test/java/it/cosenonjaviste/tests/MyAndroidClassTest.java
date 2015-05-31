package it.cosenonjaviste.tests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import it.cosenonjaviste.R;
import it.cosenonjaviste.RobolectricGradleTestRunner;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.mvp.page.PageModel;
import it.cosenonjaviste.page.PageFragment;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.utils.SingleFragmentActivity;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class MyAndroidClassTest {

//    @Test
//    public void testWhenActivityCreatedHelloTextViewIsVisible() throws Exception {
//        Intent intent = SingleFragmentActivity.createIntent(PageFragment.class);
//        intent.putExtra(MvpFragment.MODEL, Parcels.wrap(new PageModel("http://www.cosenonjaviste.it/aaa")));
//        ActivityController<SingleFragmentActivity> controller = Robolectric
//                .buildActivity(SingleFragmentActivity.class)
//                .withIntent(intent)
//                .create().start().resume().visible();
//
//        SingleFragmentActivity activity = controller.get();
//
//        Thread.sleep(200000);
//
//        int visibility = activity.findViewById(R.id.web_view).getVisibility();
//        assertEquals(visibility, View.VISIBLE);
//    }

    @Test
    public void testGetValueFromPrefs() {
        String valueFromPrefs = getValueFromPrefs(Robolectric.application);
        assertEquals("defaultValue", valueFromPrefs);
    }

    public static String getValueFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return prefs.getString("key", "defaultValue");
    }

}
