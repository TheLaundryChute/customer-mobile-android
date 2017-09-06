package com.thelaundrychute.business.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class LocationDetails {
    @SerializedName("Distance")
    private Double distance;
    @SerializedName("hours")
    private HoursOfOperation hours;
    @SerializedName("Location")
    private Location location;

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }

    public HoursOfOperation getHours() { return hours; }
    public void setHours(HoursOfOperation hours) { this.hours = hours; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}
