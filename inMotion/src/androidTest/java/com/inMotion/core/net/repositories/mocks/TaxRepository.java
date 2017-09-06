package com.inMotion.core.net.repositories.mocks;

import com.inMotion.core.net.repositories.mocks.delegates.TaxRepositoryDelegate;
import com.inMotion.core.objects.repositories.BaseObjectRepository;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class TaxRepository extends BaseObjectRepository<TaxRepositoryDelegate, Tax> {

    public TaxRepository(TaxRepositoryDelegate delegate) {
        super(delegate, Tax.class);
    }

}
