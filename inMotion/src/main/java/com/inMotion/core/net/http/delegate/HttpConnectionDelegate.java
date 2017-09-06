package com.inMotion.core.net.http.delegate;

import com.inMotion.core.net.http.HttpRequest;

import java.io.OutputStream;

/**
 * Created by sfbechtold on 9/6/15.
 */
public class HttpConnectionDelegate implements IHttpConnectionDelegate {

    @Override
    public void didReceiveResponse(HttpRequest request, int statusCode, OutputStream stream) {

    }

    @Override
    public void didReceiveError(HttpRequest request, com.inMotion.core.Error error) {

    }
}
