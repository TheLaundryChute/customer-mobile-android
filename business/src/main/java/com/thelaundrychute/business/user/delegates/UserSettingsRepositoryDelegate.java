package com.thelaundrychute.business.user.delegates;

import com.inMotion.core.objects.lists.List;

import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.UserSettingsRepository;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class UserSettingsRepositoryDelegate implements IUserSettingsRepositoryDelegate {
    @Override
    public void repositoryDidGetOne(UserSettingsRepository repository, UserSettings entity) {

    }

    @Override
    public void repositoryDidGetNone(UserSettingsRepository repository, String id) {

    }

    @Override
    public void repositoryDidGetMany(UserSettingsRepository repository, List<UserSettings> results) {

    }

    @Override
    public void repositoryDidSave(UserSettingsRepository repository, UserSettings entity) {

    }

    @Override
    public void repositoryDidDelete(UserSettingsRepository repository, UserSettings entity) {

    }

    @Override
    public void repositoryDidFail(UserSettingsRepository repository, com.inMotion.core.Error error) {

    }
}
