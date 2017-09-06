package com.inMotion.entities.user.profile;

import android.test.AndroidTestCase;

import com.inMotion.core.*;
import com.inMotion.core.Error;
import com.inMotion.core.config.AppConfig;
import com.inMotion.entities.common.Name;
import com.inMotion.entities.common.geography.Address;
import com.inMotion.entities.common.geography.states.State;
import com.inMotion.entities.user.IUserRepositoryDelegate;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.UserRepository;
import com.inMotion.entities.user.registration.IRegistrationRepositoryDelegate;
import com.inMotion.entities.user.registration.Registration;
import com.inMotion.entities.user.registration.RegistrationRepository;
import com.inMotion.session.Context;
import com.inMotion.session.IContextDelegate;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class ProfileRepositoryTestFixture extends AndroidTestCase implements IProfileRepositoryDelegate, IContextDelegate, IRegistrationRepositoryDelegate {

    final CountDownLatch loginSignal = new CountDownLatch(1);


    public void testProfileCreation() {

        Context.init(this.getContext());
        Context.getCurrent().addResponder(this);

        Registration registration = new Registration();
        registration.setFirstName("Unit Test");
        registration.setLastName("Testing");
        registration.setPassword("Apple1");
        registration.setUserName("android20@ut.com");
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

    // Context Repository Members

    @Override
    public void contextLoginDidFail(Context session, com.inMotion.core.Error error) {

        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextLoginDidSucceed(Context session, User user) {
        ProfileRepository repository = new ProfileRepository(this);
        repository.getProfileForCurrentUser();
    }

    @Override
    public void contextRefreshWillHappen(Context session) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextRefreshDidFail(Context session, Error error) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextRefreshDidSucceed(Context session, User user) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextUserDidUpdate(Context session, User user) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextUserWillLogout(Context session, User user) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void contextUserDidLogout(Context session, User user) {
        Assert.fail();
        loginSignal.countDown();
    }


    // Profile Repository Members

    @Override
    public void profileRepositoryDidFail(ProfileRepository repository, Error error) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void profileRepositoryProfileNotFound(ProfileRepository repository, User user) {
        Assert.fail();
        loginSignal.countDown();
    }

    @Override
    public void profileRepositoryDidGetProfile(ProfileRepository repository, User user, Profile profile) {

        Name name = new Name();
        name.setFirst("Unit");
        name.setLast("Test");

        State state = new State();
        state.setName("New York");
        state.setAbbreviation("NY");

        Address address = new Address();
        address.setLineOne("123 Main ST");
        address.setCity("Central");
        address.setZipcode("16023");
        address.setState(state);

        Device device = new Device();
        device.setId("YOUR DEVICE ID");
        device.setType(DeviceType.Android.toString());


        profile.setEmailAddress("android@ut.com");
        profile.setName(name);
        profile.setAddress(address);
        profile.setDevice(device);
        profile.setTelephone("412-232-2032");


        repository.save(profile);
    }

    @Override
    public void profileRepositoryDidSave(ProfileRepository repository, Profile profile) {
        Assert.assertTrue(true);
        loginSignal.countDown();
    }

    // Registration Repository Members

    @Override
    public void registrationRepositoryDidSucceeded(RegistrationRepository repository, Registration registration) {
        Context.getCurrent().login(new User(registration.getUserName()), registration.getPassword());
    }

    @Override
    public void registrationRepositoryDidFail(RegistrationRepository repository, Error error) {
        Assert.fail();
        loginSignal.countDown();
    }
}
