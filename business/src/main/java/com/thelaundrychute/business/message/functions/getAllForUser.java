package com.thelaundrychute.business.message.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.message.Message;

import java.util.List;

/**
 * Created by Steve Baleno on 12/13/2015.
 */

public class getAllForUser extends NetFunction<emptyFuncRequest, getAllForUser.response> {

    public getAllForUser() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.MESSAGE);
    }

    public class response {

        @SerializedName("messages")
        private List<Message> mMessages;

        public List<Message> getMessages() {
            return mMessages;
        }

        public void setMessages(List<Message> mMessages) {
            this.mMessages = mMessages;
        }


    }
}
