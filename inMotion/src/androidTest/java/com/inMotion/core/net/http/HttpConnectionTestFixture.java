package com.inMotion.core.net.http;

import android.util.Log;

import com.inMotion.core.net.http.delegate.HttpConnectionDelegate;

import junit.framework.Assert;

import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sfbechtold on 9/6/15.
 */
public class HttpConnectionTestFixture extends android.test.AndroidTestCase {

    public void testGetGoogle() {
/*
        try {
            final CountDownLatch signal = new CountDownLatch(1);
            HttpConnection connection = new HttpConnection();

            HttpRequest request = HttpRequest.request(new URL("http://google.com"), HttpMethod.Get, HttpContentType.Json);
            connection.send(request, new HttpConnectionDelegate() {
                @Override
                public void didReceiveResponse(HttpRequest request, int statusCode, OutputStream stream) {


                    String result = stream.toString();


                    Log.d("inMotion", result);

                    Assert.assertTrue(true);

                    signal.countDown();
                }

                @Override
                public void didReceiveError(HttpRequest request, com.inMotion.core.Error error) {
                    Assert.fail(error.getMessage());
                    signal.countDown();
                }
            });

            signal.await();
        }
        catch ( Exception ex) {
            Assert.fail();
        }*/
    }
}
