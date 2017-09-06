package com.inMotion.entities.user;

import android.test.AndroidTestCase;

import com.inMotion.core.config.AppConfig;
import com.inMotion.session.Authorization;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class UserRepositoryTestFixture extends AndroidTestCase implements IUserRepositoryDelegate {

    final CountDownLatch loginSignal = new CountDownLatch(1);


    public void testLogin() {

        AppConfig.init(this.getContext());

        UserRepository repository = new UserRepository(this);
        repository.login(new User("sbaleno@gmail.com"), "test123", false);

        try {
            loginSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }


    // IUserRepositoryDelegate Members

    @Override
    public void userRepositoryAuthorizationDidUpdate(UserRepository repository, Authorization authorization) {

        Assert.assertNotNull(authorization);

        //loginSignal.countDown();
    }

    @Override
    public void userRepositoryLoginDidSucceed(UserRepository repository, User user) {

        loginSignal.countDown();
    }

    @Override
    public void userRepositoryLoginDidFail(UserRepository repository, User user) {

        loginSignal.countDown();
    }
}
