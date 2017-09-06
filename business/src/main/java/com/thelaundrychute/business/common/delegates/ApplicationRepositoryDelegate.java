package com.thelaundrychute.business.common.delegates;

import com.inMotion.core.objects.lists.List;
import com.thelaundrychute.business.common.ApplicationRepository;
import com.thelaundrychute.business.common.Application;

/**
 * Created by Steve Baleno on 12/9/15.
 */
public class ApplicationRepositoryDelegate implements IApplicationRepositoryDelegate {
    @Override
    public void repositoryDidGetOne(ApplicationRepository repository, Application entity) {

    }

    @Override
    public void repositoryDidGetNone(ApplicationRepository repository, String id) {

    }

    @Override
    public void repositoryDidGetMany(ApplicationRepository repository, List<Application> results) {

    }

    @Override
    public void repositoryDidSave(ApplicationRepository repository, Application entity) {

    }

    @Override
    public void repositoryDidDelete(ApplicationRepository repository, Application entity) {

    }

    @Override
    public void repositoryDidFail(ApplicationRepository repository, com.inMotion.core.Error error) {

    }
}
