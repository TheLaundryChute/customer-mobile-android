package com.thelaundrychute.business.location;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by ryancoyle on 12/29/15.
 */
public class LocationPreference implements InstanceCreator<LocationPreference> {
    @SerializedName("Types")
    private List<LocationType> types;
    @SerializedName("Location")
    private Location location;

    public List<LocationType> getTypes() {
        return this.types;
    }
    public void setTypes(List<LocationType> types) { this.types = types; }

    public Location getLocationPreference() { return this.location; }

    public  Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) { this.location = location; }

    @Override
    public LocationPreference createInstance(Type type) {
        return new LocationPreference();
    }
}
