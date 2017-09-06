package com.thelaundrychute.business.user;

import com.inMotion.core.objects.repositories.BaseObjectRepository;
import com.thelaundrychute.business.user.delegates.UserSettingsRepositoryDelegate;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class UserSettingsRepository extends BaseObjectRepository<UserSettingsRepositoryDelegate, UserSettings> {

    public UserSettingsRepository(UserSettingsRepositoryDelegate delegate) {
        super(delegate, UserSettings.class);
    }

}

