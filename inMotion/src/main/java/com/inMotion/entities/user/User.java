package com.inMotion.entities.user;

import com.google.gson.annotations.SerializedName;
import com.inMotion.entities.common.Name;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class User {
    @SerializedName("Id")
    private String id;
    @SerializedName("EmailAddress")
    private String emailAddress;
    @SerializedName("Name")
    private Name name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public User(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

