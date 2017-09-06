package com.inMotion.core.net.json;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.JsonObject;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.json.delegate.JsonConnectionDelegate;

import junit.framework.Assert;

import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class JsonConnectionTestFixture extends AndroidTestCase {

    public void testSend() {

        try {
            final CountDownLatch signal = new CountDownLatch(1);
            HttpRequest request = HttpRequest.request(new URL("https://shop.bonecollector.com/api/v1/e/api/product"), HttpMethod.Get, HttpContentType.Json);
            JsonConnection connection = new JsonConnection();

            connection.send(request, new JsonConnectionDelegate() {
                @Override
                public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
                    Assert.assertTrue(true);
                    signal.countDown();
                }

                @Override
                public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
                    signal.countDown();
                    Assert.fail();
                }
            });

            signal.await();
        }
        catch (Exception ex) {
            Assert.fail();
        }

    }
}
