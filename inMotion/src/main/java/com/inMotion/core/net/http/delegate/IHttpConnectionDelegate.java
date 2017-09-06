package com.inMotion.core.net.http.delegate;

import com.inMotion.core.net.http.HttpRequest;

import java.io.OutputStream;

/**
 * Created by sfbechtold on 9/6/15.
 */
public interface IHttpConnectionDelegate {

    void didReceiveResponse(HttpRequest request, int statusCode, OutputStream stream);

    void didReceiveError(HttpRequest request, com.inMotion.core.Error error);
}
