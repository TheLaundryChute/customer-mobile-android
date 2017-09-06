package com.inMotion.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class BaseActivity extends Activity implements IBaseActivity {


    public boolean getRequiresUser() {
        return true;
    }

    public void moveToActivity(Class<?> viewType) {
        this.moveToActivity(viewType, false, null);
    }

    public void moveToActivity(Class<?> viewType, Bundle extras) {
        this.moveToActivity(viewType, false, extras);
    }

    public void moveToActivity(Class<?> viewType, boolean clearAll) {
        this.moveToActivity(viewType, clearAll, null);
    }

    public void moveToActivity(Class<?> viewType, boolean clearAll, Bundle extras) {
        InMotionActivityUtils.moveToActivity(this, viewType, clearAll, extras);
    }


    // Convinece



}
