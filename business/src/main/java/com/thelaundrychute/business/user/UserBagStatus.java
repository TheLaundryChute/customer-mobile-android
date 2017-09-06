package com.thelaundrychute.business.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class UserBagStatus {
    @SerializedName("Name")
    private String mName;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("Description")
    private String mDescription;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }


}
