package com.thelaundrychute.business.common.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.common.Feedback;
import com.thelaundrychute.business.user.UserBagHistory;
import com.thelaundrychute.business.user.Wash;

import java.util.ArrayList;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class addFeedback extends NetFunction<addFeedback.request, addFeedback.response> {
    public addFeedback() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.COMMON);
    }

    public class request {
        @SerializedName("wash")
        private Wash mWash;
        @SerializedName("washId")
        private String mWashId;
        @SerializedName("rating")
        private double mRating;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("feedbackType")
        private String mFeedbackType;

        public String getWashId() { return mWashId; }
        public void setWashId(String mWashId) { this.mWashId = mWashId; }

        public Wash getWash() { return mWash; }
        public void setWash(Wash mWash) { this.mWash = mWash; }

        public double getRating() { return mRating; }
        public void setRating(double mRating) { this.mRating = mRating; }

        public String getMessage() { return mMessage; }
        public void setMessage(String mMessage) { this.mMessage = mMessage; }

        public String getFeedbackType() { return mFeedbackType; }
        public void setFeedbackType(String mFeedbackType) { this.mFeedbackType = mFeedbackType; }
    }


    public class response {
        @SerializedName("feedback")
        private Feedback mFeedback;

        public Feedback getFeedback() { return mFeedback; }
        public void setFeedback(Feedback feedback) { this.mFeedback = feedback; }
    }


}