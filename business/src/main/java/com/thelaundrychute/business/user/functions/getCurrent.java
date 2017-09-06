package com.thelaundrychute.business.user.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.user.UserSettings;

import java.util.Map;

/**
 * Created by Steve Baleno on 12/13/2015.
 */

public class getCurrent extends NetFunction<emptyFuncRequest, getCurrent.response> {

    public getCurrent() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.USER);
    }

    public class response {
        @SerializedName("user")
        private UserSettings mUser;

        @SerializedName("details")
        private details mDetails;

        public details getDetails() {
            return mDetails;
        }

        public void details(details mDetails) {
            this.mDetails = mDetails;
        }

        public UserSettings getUser() {
            return mUser;
        }

        public void setUser(UserSettings mUser) {
            this.mUser = mUser;
        }




    }

    public class details {
        @SerializedName("actionItem")
        private actionItem mActionItem;

        public actionItem getActionItem() {
            return mActionItem;
        }

        public void setActionItem(actionItem mActionItem) {
            this.mActionItem = mActionItem;
        }

    }

    public class actionItem {
        @SerializedName("message")
        private String mMessage;

        @SerializedName("target")
        private String mTarget;

        @SerializedName("parameters")
        private Map<String, String> mParameters;

        public Map<String, String> getParameters() { return mParameters; }

        public void setParameters(Map<String, String> mParameters) { this.mParameters = mParameters; }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String mMessage) {
            this.mMessage = mMessage;
        }

        public String getTarget() {
            return mTarget;
        }

        public void setTarget(String mTarget) {
            this.mTarget = mTarget;
        }


    }

}
