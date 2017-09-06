package com.inMotion.entities.common.geography.states;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class State {

    @SerializedName("Abbreviation")
    private String abbreviation = null;

    @SerializedName("Name")
    private String name = null;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
