package com.thelaundrychute.business.location;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ryancoyle on 12/30/15.
 */
public class SpecialDays {
    @SerializedName("Label")
    private String label;
    @SerializedName("StartDate")
    private Date startDate;
    @SerializedName("EndDate")
    private Date endDate;
    @SerializedName("Status")
    private LocationStatus status;
}
