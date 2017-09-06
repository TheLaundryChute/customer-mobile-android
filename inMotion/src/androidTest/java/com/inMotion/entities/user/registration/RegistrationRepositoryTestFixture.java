package com.inMotion.entities.user.registration;

import android.test.AndroidTestCase;

import com.inMotion.core.*;
import com.inMotion.core.config.AppConfig;
import com.inMotion.entities.user.IUserRepositoryDelegate;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.UserRepository;
import com.inMotion.session.Authorization;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class RegistrationRepositoryTestFixture extends AndroidTestCase implements IRegistrationRepositoryDelegate, IUserRepositoryDelegate {

    final CountDownLatch loginSignal = new CountDownLatch(1);

    public  void testRegister() {

        AppConfig.init(this.getContext());

        Registration registration = new Registration();
        registration.setFirstName("Unit Test");
        registration.setLastName("Testing");
        registration.setPassword("Apple1");
        registration.setUserName("android3@ut.com");
        registration.setConfirmPassword("Apple1");

        RegistrationRepository repository = new RegistrationRepository(this);
        repository.register(registration);

        try {
            loginSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);

    }

    @Override
    public void registrationRepositoryDidSucceeded(RegistrationRepository repository, Registration registration) {

        UserRepository userRepository = new UserRepository(this);
        userRepository.login(new User(registration.getUserName()), registration.getPassword(), false);
    }

    @Override
    public void registrationRepositoryDidFail(RegistrationRepository repository, com.inMotion.core.Error error) {
        Assert.fail(error.getMessage());
        loginSignal.countDown();
    }

    @Override
    public void userRepositoryAuthorizationDidUpdate(UserRepository repository, Authorization authorization) {

    }

    @Override
    public void userRepositoryLoginDidSucceed(UserRepository repository, User user) {

        loginSignal.countDown();
    }

    @Override
    public void userRepositoryLoginDidFail(UserRepository repository, User user) {
        Assert.fail("Failed to login with newly registrered user");
        loginSignal.countDown();
    }
}
