package com.inMotion.core.net.repositories.mocks;

import com.google.gson.annotations.SerializedName;
import com.inMotion.core.objects.BaseObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class Tax extends BaseObject {

    @SerializedName("Zip")
    private String zip = null;
    @SerializedName("StateAbbreviation")
    private String stateAbbreviation = null;
    @SerializedName("Amount")
    private Double amount = null;


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
