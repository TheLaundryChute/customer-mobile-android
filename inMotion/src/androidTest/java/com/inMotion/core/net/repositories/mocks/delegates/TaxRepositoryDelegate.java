package com.inMotion.core.net.repositories.mocks.delegates;

import com.inMotion.core.*;
import com.inMotion.core.net.repositories.mocks.Tax;
import com.inMotion.core.net.repositories.mocks.TaxRepository;
import com.inMotion.core.objects.lists.List;
import com.inMotion.core.objects.repositories.delegates.IBaseObjectRepositoryDelegate;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class TaxRepositoryDelegate implements ITaxRepositoryDelegate{
    @Override
    public void repositoryDidGetOne(TaxRepository repository, Tax entity) {

    }

    @Override
    public void repositoryDidGetNone(TaxRepository repository, String id) {

    }

    @Override
    public void repositoryDidGetMany(TaxRepository repository, List<Tax> results) {

    }

    @Override
    public void repositoryDidSave(TaxRepository repository, Tax entity) {

    }

    @Override
    public void repositoryDidDelete(TaxRepository repository, Tax entity) {

    }

    @Override
    public void repositoryDidFail(TaxRepository repository, com.inMotion.core.Error error) {

    }
}
