package com.thelaundrychute.business.location;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by ryancoyle on 12/29/15.
 */
public class LocationType {
    @SerializedName("Name")
    private String name;
    @SerializedName("Label")
    private String label;

    public String getName() {
        return this.name;
    }
    public void setName(String name) { this.name = name; }

    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) { this.label = label; }
}

