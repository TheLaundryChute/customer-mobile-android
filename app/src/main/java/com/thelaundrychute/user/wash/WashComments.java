package com.thelaundrychute.user.wash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.fragments.SingleFragmentActivity;

public class WashComments extends SingleFragmentActivity {

    private WashCommentsFragment mCurrentFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, WashComments.class);
    }

    @Override
    protected Fragment createFragment() {
        this.mCurrentFragment = WashCommentsFragment.newInstance();
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

}
