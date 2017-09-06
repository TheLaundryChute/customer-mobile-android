package com.thelaundrychute.user.location;

import android.support.v4.app.Fragment;

import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class LocationListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new LocationListFragment();
    }
}
