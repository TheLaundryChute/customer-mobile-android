package com.thelaundrychute.user;

import android.test.AndroidTestCase;

import com.inMotion.entities.user.IUserRepositoryDelegate;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.UserRepository;
import com.inMotion.session.Authorization;

/**
 * Created by sfbechtold on 12/1/15.
 */
public class ConfigurationTestFixture extends AndroidTestCase implements IUserRepositoryDelegate  {

    public void testLoad() {

        UserRepository uRepo = new UserRepository(this);

        //AppConfig.init(this.getContext());
        //Assert.assertEquals("ten", AppConfig.getCurrent().getOrganization());

    }

    @Override
    public void userRepositoryAuthorizationDidUpdate(UserRepository repository, Authorization authorization) {

    }

    @Override
    public void userRepositoryLoginDidSucceed(UserRepository repository, User user) {

    }

    @Override
    public void userRepositoryLoginDidFail(UserRepository repository, User user) {

    }
}
