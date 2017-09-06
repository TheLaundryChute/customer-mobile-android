package com.thelaundrychute.business.common;

import com.google.gson.annotations.SerializedName;
import com.inMotion.core.objects.BaseObject;

import java.util.*;

/**
 * Created by Steve Baleno on 7/19/2016.
 */
public class Application extends BaseObject {
    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("Versions")
    private ArrayList<ApplicationVersion> versions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ApplicationVersion> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<ApplicationVersion> versions) {
        this.versions = versions;
    }
}
