package com.inMotion.core.net.http;

/**
 * Created by sfbechtold on 9/6/15.
 */
public enum HttpMethod {
    Get ("GET"),
    Put ("PUT"),
    Post ("POST"),
    Patch  ("PATCH"),
    Delete  ("DELETE");

    private final String name;

    private HttpMethod(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
    
}
