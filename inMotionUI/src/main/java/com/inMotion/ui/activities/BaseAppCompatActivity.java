package com.inMotion.ui.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by sfbechtold on 12/6/15.
 */
public class BaseAppCompatActivity extends AppCompatActivity implements IBaseActivity {


    @Override
    public boolean getRequiresUser() {
        return false;
    }

    @Override
    public void moveToActivity(Class<?> viewType) {
        InMotionActivityUtils.moveToActivity(this, viewType);
    }
}
