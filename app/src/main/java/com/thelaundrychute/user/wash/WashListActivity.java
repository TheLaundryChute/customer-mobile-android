package com.thelaundrychute.user.wash;

import android.support.v4.app.Fragment;

import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class WashListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WashListFragment();
    }

}
