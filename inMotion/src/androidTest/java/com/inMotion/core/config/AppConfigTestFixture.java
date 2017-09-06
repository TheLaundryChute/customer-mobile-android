package com.inMotion.core.config;

import junit.framework.Assert;

/**
 * Created by sfbechtold on 11/30/15.
 */
public class AppConfigTestFixture extends android.test.AndroidTestCase {

    public void testLoad() {
        AppConfig.init(this.getContext());

        Assert.assertEquals("tlc", AppConfig.getCurrent().getOrganization());
        Assert.assertEquals("ngAuthApp", AppConfig.getCurrent().getClientId());
        Assert.assertFalse(AppConfig.getCurrent().getIsCustomer());

        Assert.assertNotNull(AppConfig.getCurrent().getNetwork());
        Assert.assertEquals("http://api.inmotionapptest.net/v1", AppConfig.getCurrent().getNetwork().getHost().toString());

    }
}
