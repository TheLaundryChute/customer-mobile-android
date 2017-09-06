package com.thelaundrychute.business.bag.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.bag.Bag;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.Wash;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class finishCycle extends NetFunction<finishCycle.request, finishCycle.response> {
    public finishCycle() {
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
        @SerializedName("doReactivate")
        private boolean mDoReactivate;

        public String getBagId() {
            return mBagId;
        }
        public void setBagId(String mBagId) { this.mBagId = mBagId; }

        public String getLocationId() { return mLocationId; }
        public void setLocationId(String locationId) { this.mLocationId = locationId; }

        public boolean isDoReactivate() { return mDoReactivate; }

        public void setDoReactivate(boolean mDoReactivate) { this.mDoReactivate = mDoReactivate; }
    }

    public class response {
        @SerializedName("user")
        private UserSettings mUser;
        @SerializedName("bag")
        private Bag mBag;

        public UserSettings getUser() { return mUser; }
        public void setUser(UserSettings mUser) { this.mUser = mUser; }

        public Bag getBag() { return mBag; }
        public void setBag(Bag mBag) { this.mBag = mBag; }

    }

}
