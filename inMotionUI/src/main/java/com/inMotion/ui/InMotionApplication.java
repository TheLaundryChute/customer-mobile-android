package com.inMotion.ui;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;

import com.inMotion.core.config.AppConfig;
import com.inMotion.session.Context;
import com.inMotion.ui.activities.BaseActivity;
import com.inMotion.ui.activities.IBaseActivity;
import com.inMotion.ui.activities.login.LoginActivity;

/**
 * Created by sfbechtold on 11/30/15.
 */
public class InMotionApplication extends Application implements ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        Context.init(this); // Setup the base Application Context
        registerActivityLifecycleCallbacks(this); // Listen to events on the activities
    }



    //ActivityLifecycleCallbacks Members

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
       if (Context.getCurrent().getUser() == null &&
               !(activity instanceof LoginActivity) &&
               activity instanceof IBaseActivity) {
           IBaseActivity inMotionActivity = (IBaseActivity) activity;
           if (inMotionActivity.getRequiresUser()) {
               inMotionActivity.moveToActivity(LoginActivity.class);
           }
       }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
