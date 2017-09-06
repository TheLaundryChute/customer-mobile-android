package com.thelaundrychute.business.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Steve Baleno on 7/19/2016.
 */
public class ApplicationVersion {
    @SerializedName("Revision")
    private String revision;
    @SerializedName("Name")
    private String name;
    @SerializedName("Notes")
    private String notes;
    @SerializedName("Obsolete")
    private Boolean obsolete;


    public Boolean getObsolete() {
        return obsolete;
    }

    public void setObsolete(Boolean obsolete) {
        this.obsolete = obsolete;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
}
