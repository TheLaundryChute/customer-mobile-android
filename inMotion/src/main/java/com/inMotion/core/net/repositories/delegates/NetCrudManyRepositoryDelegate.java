package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.core.objects.lists.List;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudManyRepositoryDelegate<T extends BaseObject> implements INetCrudManyRepositoryDelegate {

    @Override
    public void netCrudRepositoryDidGetAll(NetCrudRepository repository, List results) {

    }

    @Override
    public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {

    }
}
