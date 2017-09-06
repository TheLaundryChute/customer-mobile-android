package com.thelaundrychute.business.common;

import com.inMotion.core.objects.repositories.BaseObjectRepository;
import com.thelaundrychute.business.common.Application;
import com.thelaundrychute.business.common.delegates.ApplicationRepositoryDelegate;


/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class ApplicationRepository extends BaseObjectRepository<ApplicationRepositoryDelegate, Application> {

    public ApplicationRepository(ApplicationRepositoryDelegate delegate) {
        super(delegate, Application.class);
    }

}

