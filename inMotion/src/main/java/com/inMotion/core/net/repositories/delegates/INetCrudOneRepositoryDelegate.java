package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetCrudOneRepositoryDelegate<T extends BaseObject> extends INetCrudRepositoryDelegate {

    void netCrudRepositoryDidGetOne(NetCrudRepository<T> repository, T result);

    void netCrudRepositoryDidGetNone(NetCrudRepository<T> repository, String id);
}
