package com.inMotion;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.inMotion.core.config.AppConfig;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        // Need this for other methods
        AppConfig.init(this.getContext());
    }
}