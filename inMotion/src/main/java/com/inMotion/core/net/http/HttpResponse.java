package com.inMotion.core.net.http;

import com.inMotion.core.*;

import java.io.OutputStream;

/**
 * Created by sfbechtold on 9/5/15.
 */
public class HttpResponse {

    private com.inMotion.core.Error error;
    private int statusCode = -1;
    private OutputStream content;

    public HttpResponse(com.inMotion.core.Error error) {
        this.error = error;
    }

    public HttpResponse(int statusCode, OutputStream content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public com.inMotion.core.Error getError() {
        return error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public OutputStream getContent() {
        return content;
    }
}
