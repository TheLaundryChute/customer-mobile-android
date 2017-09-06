package com.inMotion.core.net.repositories.funcs;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Steve Baleno on 12/13/2015.
 */
public class FuncResponse<T> {
    @SerializedName("success")
    private boolean mSuccess;

    @SerializedName("messages")
    private List<String> mMessages;

    @SerializedName("data")
    private T mData;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }


    public List<String> getMessages() {
        return mMessages;
    }

    public void setMessages(List<String> mMessages) {
        this.mMessages = mMessages;
    }

    public boolean isSuccess() {

        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

}
