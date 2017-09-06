package com.inMotion.entities.user;

import com.inMotion.session.Authorization;

/**
 * Created by sfbechtold on 9/7/15.
 */
public interface IUserRepositoryDelegate {
    void userRepositoryAuthorizationDidUpdate(UserRepository repository, Authorization authorization);

    void userRepositoryLoginDidSucceed(UserRepository repository, User user);
    void userRepositoryLoginDidFail(UserRepository repository, User user);
}
