package com.inMotion.core.objects.repositories.delegates;

import com.inMotion.core.*;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.core.objects.lists.List;

/**
 * Created by sfbechtold on 12/9/15.
 */
public interface IBaseObjectRepositoryDelegate<T, O extends BaseObject> {

    void repositoryDidGetOne(T repository, O entity);
    void repositoryDidGetNone(T repository, String id);
    void repositoryDidGetMany(T repository, List<O> results);

    void repositoryDidSave(T repository, O entity);
    void repositoryDidDelete(T repository, O entity);

    void repositoryDidFail(T repository, com.inMotion.core.Error error);

}
