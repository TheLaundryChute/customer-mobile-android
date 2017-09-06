package com.inMotion.entities.user.profile;

/**
 * Created by sfbechtold on 12/2/15.
 */

public enum DeviceType {
    Android ("2"),
    iOS ("1");

    private final String name;

    private DeviceType(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }

}

