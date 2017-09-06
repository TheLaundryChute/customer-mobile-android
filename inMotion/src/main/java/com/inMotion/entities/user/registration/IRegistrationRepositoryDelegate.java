package com.inMotion.entities.user.registration;

import com.inMotion.core.*;

/**
 * Created by sfbechtold on 12/2/15.
 */
public interface IRegistrationRepositoryDelegate {
    void registrationRepositoryDidSucceeded(RegistrationRepository repository, Registration registration);
    void registrationRepositoryDidFail(RegistrationRepository repository, com.inMotion.core.Error error);
}
