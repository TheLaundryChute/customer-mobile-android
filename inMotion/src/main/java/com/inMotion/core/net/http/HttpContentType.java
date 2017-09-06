package com.inMotion.core.net.http;

/**
 * Created by sfbechtold on 9/6/15.
 */
public enum HttpContentType {
    Html ("text/html"),
    Json ("application/json"),
    Form ("application/x-www-form-urlencoded");

    private final String name;

    private HttpContentType(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
