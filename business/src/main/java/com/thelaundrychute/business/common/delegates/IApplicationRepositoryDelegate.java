package com.thelaundrychute.business.common.delegates;

import com.inMotion.core.objects.repositories.delegates.IBaseObjectRepositoryDelegate;
import com.thelaundrychute.business.common.ApplicationRepository;
import com.thelaundrychute.business.common.Application;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.UserSettingsRepository;

/**
 * Created by Steve Baleno on 12/9/15.
 */
public interface IApplicationRepositoryDelegate
        extends IBaseObjectRepositoryDelegate<ApplicationRepository, Application> {
}
