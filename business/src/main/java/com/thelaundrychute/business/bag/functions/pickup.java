package com.thelaundrychute.business.bag.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.bag.Bag;
import com.thelaundrychute.business.user.UserBagHistory;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.Wash;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class pickup extends NetFunction<pickup.request, pickup.response> {
    public pickup() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.BAG);
    }

    public request newRequest() {
        return new request();
    }

    public class request {
        @SerializedName("bagId")
        private String mBagId;

        @SerializedName("locationId")
        private String mLocationId;

        public String getBagId() {
            return mBagId;
        }
        public void setBagId(String mBagId) { this.mBagId = mBagId; }

        public String getLocationId() { return mLocationId; }
        public void setLocationId(String locationId) { this.mLocationId = locationId; }
    }

    public class response {
        @SerializedName("user")
        private UserSettings mUser;
        @SerializedName("userWash")
        private Wash mWash;


        public UserSettings getUser() {
            return mUser;
        }

        public void setUser(UserSettings mUser) {
            this.mUser = mUser;
        }

        public Wash getWash() {
            return mWash;
        }

        public void setWash(Wash mWash) {
            this.mWash = mWash;
        }

    }

}
