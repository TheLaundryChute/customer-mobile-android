package com.thelaundrychute.business.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class Coordinates {
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Latitude")
    private double latitude;

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
}

