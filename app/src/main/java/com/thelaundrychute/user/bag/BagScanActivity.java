package com.thelaundrychute.user.bag;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class BagScanActivity extends SingleFragmentActivity {
    private BagScanFragment mCurrentFragment;


    public static Intent newIntent(Context packageContext, BagScanType scanType, String bagId, String locationId) {
        Intent intent = new Intent(packageContext, BagScanActivity.class);

        if (scanType != null) {
            intent.putExtra("scanType", scanType);
        } else {
            intent.putExtra("scanType", BagScanType.ACTIVATE);
        }

        if (bagId != null) {
            intent.putExtra("bagId", bagId);
        }

        if (locationId != null) {
            intent.putExtra("locationId", locationId);
        }

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        mCurrentFragment = new BagScanFragment();
        return mCurrentFragment;
    }

}
