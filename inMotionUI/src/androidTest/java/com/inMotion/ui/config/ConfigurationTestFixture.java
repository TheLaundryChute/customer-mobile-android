package com.inMotion.ui.config;

import com.inMotion.core.config.AppConfig;

import junit.framework.Assert;

/**
 * Created by sfbechtold on 12/1/15.
 */
public class ConfigurationTestFixture extends android.test.AndroidTestCase {

    public void testLoad() {

        AppConfig.init(this.getContext());
        Assert.assertEquals("ten", AppConfig.getCurrent().getOrganization());

    }
}
