package com.inMotion.core.net.http;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.session.Authorization;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by sfbechtold on 9/5/15.
 */
public class HttpRequest {

    private HttpMethod method = HttpMethod.Get;
    private HttpContentType contentType = HttpContentType.Json;
    private URL uri = null;
    private ByteArrayOutputStream data = new ByteArrayOutputStream();
    private Authorization authorization = null;

    public  HttpRequest(URL uri, HttpMethod method, HttpContentType contentType) {
        this.uri = uri;
        this.method = method;
        this.contentType = contentType;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpContentType getContentType() {
        return contentType;
    }

    public URL getUri() {
        return uri;
    }

    public ByteArrayOutputStream getData() {
        return data;
    }

    public void addObject(Object entity) {
        Gson parser = new Gson();
        String result = parser.toJson(entity);
        if (result != null) {
            try {
                data.write(result.getBytes("utf-8"));
                data.flush();
            }
            catch (Exception ex) {}
        }
    }

    public void addObject(BaseObject entity) {
        Gson parser = new Gson();

        entity.willSerialize();
        JsonElement result = parser.toJsonTree(entity);

        JsonObject one = null;
        if (result != null && result.getAsJsonObject() != null){
            one = entity.didSerialize(result.getAsJsonObject());
        }

        String jsonData = one.toString();
        if (result != null) {
            try {
                data.write(jsonData.getBytes("utf-8"));
                data.flush();
            }
            catch (Exception ex) {}
        }
    }

    public void appendData(Map<String, String> values) {
        String result = "";
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value == null || key == null)
                continue;

            try {
                result += String.format("&%s=%s", URLEncoder.encode(key, "utf-8"), URLEncoder.encode(value, "utf-8"));
            }
            catch (Exception ex) { }
        }


        // TODO : Handle exception here.
        try {
            data.write(result.substring(1).getBytes("utf-8"));
            data.flush();
        }
        catch (Exception ex){ }
    }


    public static HttpRequest request(URL uri, HttpMethod method, HttpContentType contentType) {
        return new HttpRequest(uri, method, contentType);
    }
}
