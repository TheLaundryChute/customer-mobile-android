package com.thelaundrychute.business.user;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class UserBag {
    @SerializedName("Id")
    private String mId;
    @SerializedName("Status")
    private UserBagStatus mStatus;
    @SerializedName("Label")
    private String mLabel;
    @SerializedName("EffectiveDate")
    private Date mEffectiveDate;
    @SerializedName("EndDate")
    private Date mEndDate;
    @SerializedName("Size")
    private Quantity mSize;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public UserBagStatus getStatus() {
        return mStatus;
    }

    public void setStatus(UserBagStatus mStatus) {
        this.mStatus = mStatus;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public Date getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(Date mEffectiveDate) {
        this.mEffectiveDate = mEffectiveDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date mEndDate) {
        this.mEndDate = mEndDate;
    }

    public Quantity getSize() {
        return mSize;
    }

    public void setSize(Quantity mSize) {
        this.mSize = mSize;
    }
}
