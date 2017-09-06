package com.inMotion.entities.user.profile;

import com.inMotion.core.Error;
import com.inMotion.entities.user.User;

/**
 * Created by sfbechtold on 12/2/15.
 */
public interface IProfileRepositoryDelegate {
    void profileRepositoryDidFail(ProfileRepository repository, Error error);

    void profileRepositoryProfileNotFound(ProfileRepository repository, User user);
    void profileRepositoryDidGetProfile(ProfileRepository repository, User user, Profile profile);

    void profileRepositoryDidSave(ProfileRepository repository, Profile profile);
}
