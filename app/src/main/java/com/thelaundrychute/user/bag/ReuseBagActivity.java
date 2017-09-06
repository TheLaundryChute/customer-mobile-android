package com.thelaundrychute.user.bag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

import android.support.v4.app.Fragment;

public class ReuseBagActivity extends SingleFragmentActivity {

    private ReuseBagActivityFragment mCurrentFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, ReuseBagActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        this.mCurrentFragment = ReuseBagActivityFragment.newInstance();
        return this.mCurrentFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar_bottom);
        toolbar.setVisibility(View.INVISIBLE);
        toolbar.getLayoutParams().height = 0;
        toolbar.setLayoutParams(toolbar.getLayoutParams());
    }

    @Override
    public void onBackPressed() {
    }


}
