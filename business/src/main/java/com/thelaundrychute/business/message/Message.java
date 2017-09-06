package com.thelaundrychute.business.message;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Steve Baleno on 12/13/2015.
 */
public class Message {
    @SerializedName("Id")
    private String mId;
    //private List<Recipient> mRecipients;
    //private Date mDeliversOn;
    @SerializedName("Subject")
    private String mSubject;
    @SerializedName("Content")
    private String mContent;
    @SerializedName("From")
    private String mFrom;

    public String getId() {
        return mId;
    }

    public void seId(String mId) {
        this.mId = mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public void seSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getContent() {
        return mContent;
    }

    public void seContent(String mContent) {
        this.mContent = mContent;
    }

    public String getFrom() {
        return mFrom;
    }

    public void seFrom(String mFrom) {
        this.mFrom = mFrom;
    }
}
