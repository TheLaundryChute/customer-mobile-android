package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetCrudDeleteRepositoryDelegate<T extends BaseObject> extends INetCrudRepositoryDelegate<T> {
    void netCrudRepositoryDidDelete(NetCrudRepository<T> repository, T result);
}
