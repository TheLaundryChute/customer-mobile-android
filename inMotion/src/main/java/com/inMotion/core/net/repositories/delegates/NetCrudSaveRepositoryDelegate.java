package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.*;
import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudSaveRepositoryDelegate<T extends BaseObject> implements INetCrudSaveRepositoryDelegate<T> {
    @Override
    public void netCrudRepositoryDidSave(NetCrudRepository<T> repository, T result) {

    }

    @Override
    public void netCrudRepositoryDidFail(NetCrudRepository<T> repository, com.inMotion.core.Error error) {

    }
}
