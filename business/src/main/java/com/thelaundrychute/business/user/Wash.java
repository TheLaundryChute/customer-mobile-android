package com.thelaundrychute.business.user;

import android.support.annotation.NonNull;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;
import com.thelaundrychute.business.location.Location;
import com.thelaundrychute.business.location.LocationPreference;
import com.thelaundrychute.business.location.LocationType;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public class Wash {
    @SerializedName("Id")
    private long mId;
    @SerializedName("Bag")
    private UserBag mBag;
    @SerializedName("OrderId")
    private String mOrderId;
    @SerializedName("AvailableOn")
    private Date mAvailableOn;
    @SerializedName("ExpiresOn")
    private Date mExpiresOn;
    @SerializedName("Locations")
    private List<WashLocation> mLocations;
    @SerializedName("WashPreferences")
    private WashPreferences mWashPreferences;
    @SerializedName("Garments")
    private List<Garment> mGarments;


    public Wash() {
        this(0);
    }

    public Wash(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public UserBag getBag() {
        return mBag;
    }

    public void setBag(UserBag mBag) {
        this.mBag = mBag;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public Date getAvailableOn() {
        return mAvailableOn;
    }

    public void setAvailableOn(Date mAvailableOn) {
        this.mAvailableOn = mAvailableOn;
    }

    public Date getExpiresOn() {
        return mExpiresOn;
    }

    public void setExpiresOn(Date mExpiresOn) {
        this.mExpiresOn = mExpiresOn;
    }

    public List<WashLocation> getLocations() {
        return mLocations;
    }

    public void setLocations(List<WashLocation> mLocations) {
        this.mLocations = mLocations;
    }

    public WashPreferences getWashPreferences() {
        return mWashPreferences;
    }

    public void setWashPreferences(WashPreferences mWashPreferences) {
        this.mWashPreferences = mWashPreferences;
    }

    public List<Garment> getGarments() {
        return mGarments;
    }

    public void setGarments(List<Garment> mGarments) {
        this.mGarments = mGarments;
    }


    public Location getPickupLocation() {
        if (this.mLocations == null) {
            return null;
        }

        for (WashLocation location : mLocations) {

            LocationPreference locationPreference = location.getLocation();
            List<LocationType> preferenceTypes = locationPreference.getTypes();

            if (preferenceTypes.size() >= 1) {

                for (LocationType type : preferenceTypes) {
                    if (type.getName().contentEquals("PICK_UP")) {
                        return locationPreference.getLocation();
                    }

                }
            }
        }
        return null;
    }
}
