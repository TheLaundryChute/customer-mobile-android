package com.inMotion.ui.activities;

/**
 * Created by sfbechtold on 12/2/15.
 */
public interface IBaseActivity {

    boolean getRequiresUser();

    void moveToActivity(Class<?> viewType);
}
