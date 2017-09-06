package com.thelaundrychute.business.location.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.location.Coordinates;
import com.thelaundrychute.business.location.Location;
import com.thelaundrychute.business.location.LocationDetails;

import java.util.ArrayList;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class getLocationsByTypes extends NetFunction<getLocationsByTypes.request, getLocationsByTypes.response> {
    public getLocationsByTypes() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.LOCATION);
    }

    public request newRequest() {
        return new request();
    }

    public class request {
        @SerializedName("types")
        private String types;
        @SerializedName("coordinates")
        private Coordinates coordinates;
        @SerializedName("radius")
        private double radius;

        public String getTypes() { return types; }
        public void setTypes(String types) { this.types = types; }

        public Coordinates getCoordinates() { return coordinates; }
        public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

        public double getRadius() { return radius; }
        public void setRadius(double radius) { this.radius = radius; }
    }


    public class response {
        @SerializedName("locations")
        private ArrayList<LocationDetails> locations;

        public ArrayList<LocationDetails> getLocations() { return locations; }
        public void setLocations(ArrayList<LocationDetails> locations) { this.locations = locations; }
    }


}