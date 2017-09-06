package com.thelaundrychute.business.user.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.user.UserBagHistory;

import java.util.ArrayList;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class getAllUserBagHistoryByWashId extends NetFunction<getAllUserBagHistoryByWashId.request, getAllUserBagHistoryByWashId.response> {
    public getAllUserBagHistoryByWashId() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.USER);
    }

    public class request {
        @SerializedName("washId")
        private String mWashId;

        public String getWashId() { return mWashId; }
        public void setWashId(String mWashId) { this.mWashId = mWashId; }
    }


    public class response {
        @SerializedName("userBagHistories")
        private ArrayList<UserBagHistory> mUserBagHistories;

        public ArrayList<UserBagHistory> getUserBagHistory() { return mUserBagHistories; }
        public void setUserBagHistory(ArrayList<UserBagHistory> userBagHistories) { this.mUserBagHistories = userBagHistories; }
    }


}
