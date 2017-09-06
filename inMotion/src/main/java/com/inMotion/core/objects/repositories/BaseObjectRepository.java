package com.inMotion.core.objects.repositories;

import com.inMotion.core.net.repositories.NetCrudRepository;
import com.inMotion.core.net.repositories.delegates.*;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.core.objects.lists.List;
import com.inMotion.core.objects.queries.Query;
import com.inMotion.core.objects.repositories.delegates.IBaseObjectRepositoryDelegate;

/**
 * Created by sfbechtold on 12/9/15.
 */
public abstract class BaseObjectRepository<D extends IBaseObjectRepositoryDelegate, O extends BaseObject> extends NetCrudRepository<O> {

    private D delegate = null;

    protected BaseObjectRepository(D delegate, Class<O> classType) {
        super(classType);

        this.delegate = delegate;
    }

    public D getDelegate() {
        return delegate;
    }

    public void one(String guid) {
        final BaseObjectRepository<D, O> instance = this;
        this.one(guid, new NetCrudOneRepositoryDelegate<O>() {
            @Override
            public void netCrudRepositoryDidGetOne(NetCrudRepository<O> repository, O result) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidGetOne(instance, result);
                }
            }

            @Override
            public void netCrudRepositoryDidGetNone(NetCrudRepository<O> repository, String id) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidGetNone(instance, id);
                }
            }

            @Override
            public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidFail(instance, error);
                }
            }
        });
    }

    public void all(Integer size, Integer index, Query query) {
        final BaseObjectRepository<D, O> instance = this;

        this.all(size, index, query, new NetCrudManyRepositoryDelegate<O>() {
            @Override
            public void netCrudRepositoryDidGetAll(NetCrudRepository repository, List results) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidGetMany(instance, results);
                }
            }

            @Override
            public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidFail(instance, error);
                }
            }
        });
    }


    public void save(O entity) {
        final BaseObjectRepository<D, O> instance = this;
        this.save(entity, new NetCrudSaveRepositoryDelegate<O>() {
            @Override
            public void netCrudRepositoryDidSave(NetCrudRepository<O> repository, O result) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidSave(instance, result);
                }
            }

            @Override
            public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidFail(instance, error);
                }
            }
        });
    }


    public void delete(O entity) {
        final BaseObjectRepository<D, O> instance = this;
        this.delete(entity, new NetCrudDeleteRepositoryDelegate<O>() {
            @Override
            public void netCrudRepositoryDidDelete(NetCrudRepository repository, BaseObject result) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidDelete(instance, result);
                }
            }

            @Override
            public void netCrudRepositoryDidFail(NetCrudRepository repository, com.inMotion.core.Error error) {
                if (instance.getDelegate() != null) {
                    instance.getDelegate().repositoryDidFail(instance, error);
                }
            }
        });
    }


}
