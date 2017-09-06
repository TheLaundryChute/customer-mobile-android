package com.inMotion.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class InMotionActivityUtils {


    public static void moveToActivity(Activity container, Class<?> viewType) {
        InMotionActivityUtils.moveToActivity(container, viewType, false, null);
    }

    public static void moveToActivity(Activity container, Class<?> viewType, Bundle extras) {
        InMotionActivityUtils.moveToActivity(container, viewType, false, extras);
    }


    public static void moveToActivity(Activity container, Class<?> viewType, boolean clearAll, Bundle extras) {

        try {

            Intent intent = new Intent(container, viewType);
            if (clearAll)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (extras != null)
                intent.putExtras(extras);
            container.startActivity(intent);
            //container.overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
        catch (Exception ex) {
            int i = 0;
        }
    }
}
