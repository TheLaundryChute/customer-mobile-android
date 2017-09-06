package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.*;
import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudDeleteRepositoryDelegate<T extends BaseObject> implements INetCrudDeleteRepositoryDelegate {
    @Override
    public void netCrudRepositoryDidDelete(NetCrudRepository repository, BaseObject result) {

    }

    @Override
    public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {

    }
}
