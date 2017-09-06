package com.inMotion.core.net.repositories;

import com.inMotion.core.config.AppConfig;
import com.inMotion.core.net.http.HttpContentType;
import com.inMotion.core.net.http.HttpMethod;
import com.inMotion.core.net.http.HttpRequest;

import java.net.URL;

/**
 * Created by sfbechtold on 12/1/15.
 */
public abstract class NetBaseRepository {

    protected HttpRequest manufactureRequest(String resource, HttpMethod method, HttpContentType contentType) {
        try {
            URL requestUrl = this.makeUrl(resource);
            return this.manufactureRequest(requestUrl, method, contentType);
        }
        catch (Exception ex) {
            return null;
        }
    }


    protected HttpRequest manufactureRequest(URL url, HttpMethod method, HttpContentType contentType) {
        try {
            return HttpRequest.request(url, method, contentType);
        }
        catch (Exception ex) {
            return  null;
        }
    }

    protected URL makeUrl(String fragment) {
        try {
            URL baseUrl = AppConfig.getCurrent().getNetwork().getHost();
            return new URL(baseUrl, fragment);
        }
        catch (Exception ex) {
            return  null;
        }
    }
}
