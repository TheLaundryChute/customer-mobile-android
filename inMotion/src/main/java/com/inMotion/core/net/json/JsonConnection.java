package com.inMotion.core.net.json;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inMotion.core.*;
import com.inMotion.core.Error;
import com.inMotion.core.net.http.HttpConnection;
import com.inMotion.core.net.http.HttpRequest;
import com.inMotion.core.net.http.delegate.HttpConnectionDelegate;
import com.inMotion.core.net.json.delegate.IJsonConnectionDelegate;

import java.io.OutputStream;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class JsonConnection  {

    public void send(HttpRequest request, final IJsonConnectionDelegate delegate) {
        final JsonConnection sender = this;
        HttpConnection connection = new HttpConnection();
        connection.send(request, new HttpConnectionDelegate() {
            @Override
            public void didReceiveResponse(HttpRequest request, int statusCode, OutputStream stream) {

                if ((statusCode > 299 && statusCode < 199) || (stream == null)) {
                    delegate.jsonConnectionDidFail(sender, Error.error(100, "Received an invalid status code from server", "JsonConnection.send"));
                    return;
                }

                try {
                    JsonParser parser = new JsonParser();


                    JsonObject raw = null;

                    String rawJson = stream.toString();
                    if (rawJson != null && rawJson.length() > 5) {
                        raw = (JsonObject) parser.parse(stream.toString());
                    }

                    if (raw == null) {
                        delegate.jsonConnectionDidSucceedWithNoResult(sender, statusCode);
                        return;
                    }
                    delegate.jsonConnectionDidSucceed(sender, statusCode, raw);
                }
                catch (Exception ex) {
                    Log.d("inMotion.core", "Exception parsing JSON: " + ex.getLocalizedMessage());
                    delegate.jsonConnectionDidFail(sender, Error.error(100, "Invalid JSON Format received", "JsonConnection.send"));
                }
            }

            @Override
            public void didReceiveError(HttpRequest request, com.inMotion.core.Error error) {

                if (delegate != null) {
                    delegate.jsonConnectionDidFail(sender, com.inMotion.core.Error.error(100, "an invalid response was received from the server", "JsonConnection.send"));
                }
            }
        });
    }



}
