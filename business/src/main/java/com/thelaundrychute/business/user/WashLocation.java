package com.thelaundrychute.business.user;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;
import com.thelaundrychute.business.location.LocationPreference;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class WashLocation {
//    @SerializedName("EffectiveDate")
//    private Date effectiveDate;
//    @SerializedName("EndDate")
//    private Date endDate;
    @SerializedName("Location")
    private LocationPreference location;


    public LocationPreference getLocation() {
        return this.location;
    }
    public void setLocation(LocationPreference preference) { this.location = preference; }
//    public Date getEffectiveDate() { return this.effectiveDate; }
//    public void setEffectiveDate(Date date) { this.effectiveDate = date; }
//    public Date getEndDate() { return this.endDate; }
//    public void setEndDate(Date date) { this.endDate = date; }

}

