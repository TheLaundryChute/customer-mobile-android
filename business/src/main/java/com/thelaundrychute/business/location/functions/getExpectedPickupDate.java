package com.thelaundrychute.business.location.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.location.Coordinates;
import com.thelaundrychute.business.location.LocationDetails;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class getExpectedPickupDate extends NetFunction<getExpectedPickupDate.request, getExpectedPickupDate.response> {
    public getExpectedPickupDate() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.LOCATION);
    }

    public request newRequest() {
        return new request();
    }

    public class request {
        @SerializedName("washId")
        private long washId;

        public long getWashId() {
            return washId;
        }

        public void setWashId(long washId) {
            this.washId = washId;
        }
    }


    public class response {
        @SerializedName("expectedPickupDate")
        private Date expectedPickupDate;

        public Date getExpectedPickupDate() { return expectedPickupDate; }
        public void setExpectedPickupDate(Date expectedPickupDate) { this.expectedPickupDate = expectedPickupDate; }
    }


}