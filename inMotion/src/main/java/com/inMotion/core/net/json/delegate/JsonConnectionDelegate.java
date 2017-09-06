package com.inMotion.core.net.json.delegate;

import android.util.Log;

import com.inMotion.core.*;
import com.inMotion.core.net.json.JsonConnection;

import com.google.gson.JsonObject;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class JsonConnectionDelegate implements IJsonConnectionDelegate {
    @Override
    public void jsonConnectionDidSucceed(JsonConnection connection, int statusCode, JsonObject result) {
        Log.d("inMotion.Core", "Json Connection Success method was not implemented by delegate");
    }

    @Override
    public void jsonConnectionDidFail(JsonConnection connection, com.inMotion.core.Error error) {
        Log.d("inMotion.Core", "Json Connection Fail method was not implemented by delegate");
    }

    @Override
    public void jsonConnectionDidSucceedWithNoResult(JsonConnection connection, int statusCode) {
        Log.d("inMotion.Core", "Json Connection Empty method was not implemented by delegate");
    }
}
