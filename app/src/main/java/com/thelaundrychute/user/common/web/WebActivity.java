package com.thelaundrychute.user.common.web;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class WebActivity extends SingleFragmentActivity {


    private static WebFragment mCurrentFragment;

    public static Intent newIntent(Context context, String webTarget, String... args) {
        Intent intent = new Intent(context, WebActivity.class);
        for(String arg : args) {
                webTarget += arg;
        }
        intent.putExtra(WebFragment.ARG_WEB_TARGET, webTarget);
        return intent;
    }

    public static void onReceiveNotification(String message) {
        mCurrentFragment.onReceiveNotification();;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = createFragment();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction()
                .replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected Fragment createFragment() {
        String webTarget = (String) getIntent().getSerializableExtra(WebFragment.ARG_WEB_TARGET);
        mCurrentFragment = WebFragment.newInstance(webTarget);
        return mCurrentFragment;
    }

    @Override
    public void onBackPressed()
    {
        //If its true, the web fragment handled it.  If not, we will default to normal
        if (!mCurrentFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }


}
