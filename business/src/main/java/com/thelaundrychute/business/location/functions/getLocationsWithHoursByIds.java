package com.thelaundrychute.business.location.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.location.Coordinates;
import com.thelaundrychute.business.location.HoursOfOperation;
import com.thelaundrychute.business.location.Location;
import com.thelaundrychute.business.location.LocationDetails;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KalEl on 12/14/2015.
 */
public class getLocationsWithHoursByIds extends NetFunction<getLocationsWithHoursByIds.request, getLocationsWithHoursByIds.response> {
    public getLocationsWithHoursByIds() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.LOCATION);
    }

    public class request {
        @SerializedName("locationIds")
        private ArrayList<String> locationIds;
        @SerializedName("dropoffDate")
        private String dropoffDate;
        @SerializedName("locationTypes")
        private ArrayList<String> locationTypes;

        public ArrayList<String> getLocationIds() { return locationIds; }
        public void setLocationIds(ArrayList<String> locationIds) { this.locationIds = locationIds; }

        public String getDropoffDate() { return dropoffDate; }
        public void setDropoffDate(String dropoffDate) { this.dropoffDate = dropoffDate; }

        public ArrayList<String> getLocationTypes() { return locationTypes; }
        public void setLocationTypes(ArrayList<String> locationTypes) { this.locationTypes = locationTypes; }
    }

    public class response {
        @SerializedName("locations")
        private ArrayList<Location> locations;
        @SerializedName("hours")
        private HoursOfOperation hours;
        private Date expectedPickupDate;
        public ArrayList<Location> getLocations() { return locations; }
        public void setLocations(ArrayList<Location> locations) { this.locations = locations; }
    }


}