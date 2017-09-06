package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudOneRepositoryDelegate<T extends BaseObject> implements INetCrudOneRepositoryDelegate<T> {
    @Override
    public void netCrudRepositoryDidGetOne(NetCrudRepository<T> repository, T result) {

    }

    @Override
    public void netCrudRepositoryDidGetNone(NetCrudRepository<T> repository, String id) {

    }

    @Override
    public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {

    }
}
