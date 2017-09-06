package com.inMotion.core.net.repositories;

import android.test.AndroidTestCase;

import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.mocks.delegates.TaxRepositoryDelegate;
import com.inMotion.core.net.repositories.mocks.Tax;
import com.inMotion.core.net.repositories.mocks.TaxRepository;
import com.inMotion.core.objects.lists.List;
import com.inMotion.session.Context;

import junit.framework.Assert;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class NetCrudRepositoryTestFixture extends AndroidTestCase {

    public void testGetOne() {

        try {
            Context.init(this.getContext());

            final CountDownLatch signal = new CountDownLatch(1);
            TaxRepository repo = new TaxRepository(new TaxRepositoryDelegate() {
                @Override
                public void repositoryDidGetOne(TaxRepository repository, Tax entity) {

                    signal.countDown();
                    Assert.assertTrue(true);
                }

                @Override
                public void repositoryDidGetNone(TaxRepository repository, String id) {
                    signal.countDown();
                    Assert.fail();
                }

                @Override
                public void repositoryDidFail(TaxRepository repository, Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });

            repo.one("560b43f8ea9ef369887b8351");
            signal.await();
        }
        catch (Exception ex) {

        }
    }


    public void testGetAll() {

        try {
            Context.init(this.getContext());

            final CountDownLatch signal = new CountDownLatch(1);
            TaxRepository repo = new TaxRepository(new TaxRepositoryDelegate() {

                @Override
                public void repositoryDidGetNone(TaxRepository repository, String id) {
                    signal.countDown();
                    Assert.fail();
                }

                @Override
                public void repositoryDidGetMany(TaxRepository repository, List<Tax> results) {
                    signal.countDown();
                    Assert.assertTrue(true);
                }

                @Override
                public void repositoryDidFail(TaxRepository repository, Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });

            repo.all(10, 0, null);
            signal.await();
        }
        catch (Exception ex) {

        }

    }


    public void testCreate() {
        try {
            Context.init(this.getContext());

            final CountDownLatch signal = new CountDownLatch(1);
            TaxRepository repo = new TaxRepository(new TaxRepositoryDelegate() {

                @Override
                public void repositoryDidSave(TaxRepository repository, Tax entity) {
                    signal.countDown();
                    Assert.assertTrue(true);
                }

                @Override
                public void repositoryDidFail(TaxRepository repository, Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });


            final Tax data = new Tax();
            data.setAmount(0.02);
            data.setStateAbbreviation("XX");
            data.setZip("20002");


            repo.save(data);

            signal.await();
        } catch (Exception ex) {

        }
    }


    public void testUpdate() {
        try {
            Context.init(this.getContext());

            final CountDownLatch signal = new CountDownLatch(1);
            final Tax fetched = null;

            TaxRepository repo = new TaxRepository(new TaxRepositoryDelegate() {

                @Override
                public void repositoryDidSave(TaxRepository repository, Tax entity) {
                    signal.countDown();
                    Assert.assertTrue(true);
                }

                @Override
                public void repositoryDidGetNone(TaxRepository repository, String id) {
                    signal.countDown();
                    Assert.fail();
                }

                @Override
                public void repositoryDidGetOne(TaxRepository repository, Tax entity) {

                    entity.setAmount(0.03);
                    repository.save(entity);

                }

                @Override
                public void repositoryDidFail(TaxRepository repository, Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });



            repo.one("560b43f8ea9ef369887b8351");
            signal.await();

            signal.await();
        } catch (Exception ex) {

        }
    }


    public void testDelete() {
        try {
            Context.init(this.getContext());

            final CountDownLatch signal = new CountDownLatch(1);
            final Tax fetched = null;

            TaxRepository repo = new TaxRepository(new TaxRepositoryDelegate() {

                @Override
                public void repositoryDidSave(TaxRepository repository, Tax entity) {
                    repository.one(entity.getInstance().getId());
                }

                @Override
                public void repositoryDidGetNone(TaxRepository repository, String id) {
                    signal.countDown();
                    Assert.fail();
                }

                @Override
                public void repositoryDidGetOne(TaxRepository repository, Tax entity) {

                    repository.delete(entity);

                }

                @Override
                public void repositoryDidDelete(TaxRepository repository, Tax entity) {
                    signal.countDown();
                    Assert.assertTrue(true);
                }

                @Override
                public void repositoryDidFail(TaxRepository repository, Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });



            Tax data = new Tax();
            data.setAmount(0.02);
            data.setStateAbbreviation("XX");
            data.setZip("20010");

            repo.save(data);

            signal.await();

            signal.await();
        } catch (Exception ex) {

        }
    }



}
