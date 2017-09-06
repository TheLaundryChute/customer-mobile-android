package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetCrudRepositoryDelegate<T extends BaseObject> {

    void netCrudRepositoryDidFail(NetCrudRepository<T> repository, com.inMotion.core.Error error);

}
