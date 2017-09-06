package com.thelaundrychute.business.user.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;

import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.message.FeedbackMessage;

/**
 * Created by ryancoyle on 1/4/16.
 */
public class sendMessageFromUser extends NetFunction<sendMessageFromUser.request, sendMessageFromUser.response> {

    public sendMessageFromUser() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.USER);
    }

    public request newRequest() {
        return new request();
    }

    public class request {
        @SerializedName("message")
        private FeedbackMessage mMessage;
        @SerializedName("sendMessage")
        private Boolean mSendMessage;
        @SerializedName("addFeedback")
        private Boolean mAddFeedback;

        public FeedbackMessage getmMessage() {
            return mMessage;
        }

        public void setmMessage(FeedbackMessage mMessage) {
            this.mMessage = mMessage;
        }

        public Boolean getmSendMessage() {
            return mSendMessage;
        }

        public void setmSendMessage(Boolean mSendMessage) {
            this.mSendMessage = mSendMessage;
        }

        public Boolean getmAddFeedback() {
            return mAddFeedback;
        }

        public void setmAddFeedback(Boolean mAddFeedback) {
            this.mAddFeedback = mAddFeedback;
        }
    }

    public class response {

    }


}
