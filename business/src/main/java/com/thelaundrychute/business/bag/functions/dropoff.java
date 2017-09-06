package com.thelaundrychute.business.bag.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.bag.Bag;
import com.thelaundrychute.business.location.Location;
import com.thelaundrychute.business.user.UserBagHistory;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.Wash;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class dropoff extends NetFunction<dropoff.request, dropoff.response> {
    public dropoff() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.BAG);
    }

    public request newRequest() {
        return new request();
    }


    public class request {
        @SerializedName("bagId")
        private String mBagId;

        @SerializedName("binId")
        private String mBinId;

        public String getBagId() {
            return mBagId;
        }
        public void setBagId(String mBagId) { this.mBagId = mBagId; }

        public String getBinId() { return mBinId; }
        public void setBinId(String mBinId) { this.mBinId = mBinId; }
    }

    public class response {
        /*bag, location, userSettings, userBag, userBagHistory*/

        @SerializedName("user")
        private UserSettings mUser;
        @SerializedName("wash")
        private Wash mWash;
        @SerializedName("bag")
        private Bag mBag;
        @SerializedName("userBagHistory")
        private UserBagHistory mUserBagHistory;
        @SerializedName("location")
        private Location mLocation;

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

        public Bag getBag() {
            return mBag;
        }

        public void setBag(Bag mBag) {
            this.mBag = mBag;
        }

        public Location getLocation() {
            return mLocation;
        }

        public void setLocation(Location location) {
            this.mLocation = location;
        }

        public UserBagHistory getUserBagHistory() {
            return mUserBagHistory;
        }

        public void setUserBagHistory(UserBagHistory mUserBagHistory) {
            this.mUserBagHistory = mUserBagHistory;
        }
    }

}
