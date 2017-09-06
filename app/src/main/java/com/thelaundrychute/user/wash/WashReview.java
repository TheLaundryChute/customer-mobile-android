package com.thelaundrychute.user.wash;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class WashReview extends SingleFragmentActivity {

    private WashReviewFragment mCurrentFragment;

    public static Intent newIntent(Context context, String bagId, long washId, String locationGuid) {

        Intent intent =  new Intent(context, WashReview.class);

        if (bagId != null && locationGuid != null) {
            intent.putExtra("bagId", bagId);
            intent.putExtra("washId", washId);
            intent.putExtra("locationId", locationGuid);
        }

        return intent;
    }


    @Override
    protected Fragment createFragment() {
        this.mCurrentFragment = WashReviewFragment.newInstance();
        return this.mCurrentFragment;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar_bottom);
        toolbar.setVisibility(View.INVISIBLE);
        toolbar.getLayoutParams().height = 0;
        toolbar.setLayoutParams(toolbar.getLayoutParams());


       // getActionBar().hide();

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        //actionBar.hide();




    }

}
