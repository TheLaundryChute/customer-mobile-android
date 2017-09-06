package com.thelaundrychute.business.location;

import android.support.annotation.NonNull;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.entities.common.geography.Address;
import com.thelaundrychute.business.bag.Bag;
import com.thelaundrychute.business.user.TLCAddress;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class Location extends BaseObject {
    @SerializedName("Id")
    private String id;
    @SerializedName("Name")
    private String name;

    //    @SerializedName("Types")
//    private List<LocationType> types;
//    @SerializedName("Coordinates")
//    private Coordinates coordinates;
//    @SerializedName("Address")
//    private TLCAddress address;
    @SerializedName("SpecialInstructions")
    private String specialInstructions;
//    @SerializedName("Bags")
//    private List<Bag> bags;
//    @SerializedName("Bins")
//    private List<Bin> bins;
////    @SerializedName("Schedules")
//    private List<LocationSchedule> schedules;

    public String getName() {
        return this.name;
    }
    public void setName(String mName) {
        this.name = mName;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String mId) {
        this.id = mId;
    }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

//    public List<LocationType> getTypes() {
//        return types;
//    }
//
//    public void setTypes(List<LocationType> types) {
//        this.types = types;
//    }
//
//    public Coordinates getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(Coordinates coordinates) {
//        this.coordinates = coordinates;
//    }


}
