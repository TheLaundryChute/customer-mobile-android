package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetCrudSaveRepositoryDelegate<T extends BaseObject> extends INetCrudRepositoryDelegate<T> {
    void netCrudRepositoryDidSave(NetCrudRepository<T> repository, T result);
}
