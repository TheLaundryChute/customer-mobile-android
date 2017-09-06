package com.thelaundrychute.business.user;

import com.google.gson.annotations.SerializedName;
import com.inMotion.entities.common.geography.Address;

/**
 * Created by ryancoyle on 12/30/15.
 */
public class TLCAddress extends Address {
    @SerializedName("Label")
    private String label;
    @SerializedName("IsDefault")
    private Boolean isDefault;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
