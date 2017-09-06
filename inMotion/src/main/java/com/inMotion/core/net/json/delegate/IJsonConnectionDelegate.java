package com.inMotion.core.net.json.delegate;

import com.inMotion.core.*;
import com.inMotion.core.net.json.JsonConnection;

import com.google.gson.JsonObject;


/**
 * Created by sfbechtold on 9/7/15.
 */
public interface IJsonConnectionDelegate {
    void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result);
    void jsonConnectionDidSucceedWithNoResult(JsonConnection connection, int statusCode);
    void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error);
}
