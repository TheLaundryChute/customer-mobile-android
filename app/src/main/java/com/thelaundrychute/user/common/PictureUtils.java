package com.thelaundrychute.user.common;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }

    public static Drawable getScaledBitmap(Activity activity, int id, View view) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);


        Drawable d = getScaledBitmap(activity, id, view.getLayoutParams().width, view.getLayoutParams().height);

        return d;
    }

    public static Drawable getScaledBitmap(Activity activity, int id, int width, int height) {
        Resources resources = activity.getResources();
        Bitmap original = BitmapFactory.decodeResource(resources, id);

        Bitmap b = Bitmap.createScaledBitmap(original, width, height, false);

        Drawable d = new BitmapDrawable(activity.getResources(), b);
        return d;
    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        // read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(path, options);
    }
}
