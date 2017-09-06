package com.inMotion.entities;

import com.inMotion.core.config.AppConfig;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;

import java.net.URL;

/**
 * Created by sfbechtold on 12/1/15.
 */
public abstract class BaseRepository {

    protected HttpRequest manufactureRequest(String resource, HttpMethod method, HttpContentType contentType) {
        try {
            URL baseUrl = AppConfig.getCurrent().getNetwork().getHost();
            URL requestUrl = new URL(baseUrl, resource);

            return HttpRequest.request(requestUrl, method, contentType);
        }
        catch (Exception ex) {
            return  null;
        }
    }
}
