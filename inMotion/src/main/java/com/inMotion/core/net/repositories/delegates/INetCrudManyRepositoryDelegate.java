package com.inMotion.core.net.repositories.delegates;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.core.objects.lists.List;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface INetCrudManyRepositoryDelegate<T extends BaseObject> extends INetCrudRepositoryDelegate {

    void netCrudRepositoryDidGetAll(NetCrudRepository<T> repository, List<T> results);
}
