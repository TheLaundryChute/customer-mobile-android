package com.inMotion.core.net;

/**
 * Created by Steve Baleno on 12/13/2015.
 */
public enum ResourceOperationType {
    Api ("api"),
    Validation ("validate"),
    Factory ("factory"),
    Function ("func");

    private final String name;

    private ResourceOperationType(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
