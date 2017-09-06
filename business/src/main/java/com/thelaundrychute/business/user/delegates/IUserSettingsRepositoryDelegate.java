package com.thelaundrychute.business.user.delegates;

import com.inMotion.core.objects.repositories.delegates.IBaseObjectRepositoryDelegate;

import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.UserSettingsRepository;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface IUserSettingsRepositoryDelegate
        extends IBaseObjectRepositoryDelegate<UserSettingsRepository, UserSettings> {
}
