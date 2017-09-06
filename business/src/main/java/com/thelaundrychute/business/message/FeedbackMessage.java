package com.thelaundrychute.business.message;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ryancoyle on 1/4/16.
 */
public class FeedbackMessage {
    @SerializedName("subject")
    private String mSubject;
    @SerializedName("content")
    private String mContent;
    @SerializedName("washId")
    private String mWashId;
    @SerializedName("feedbackType")
    private String mFeedbackType;
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("rating")
    private Integer mRating;

    public FeedbackMessage(String subject, String content, String washId, String feedbackType, String userId, Integer rating) {
        this.mSubject = subject;
        this.mContent = content;
        this.mWashId = washId;
        this.mFeedbackType = feedbackType;
        this.mUserId = userId;
        this.mRating = rating;
    }


    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmWashId() {
        return mWashId;
    }

    public void setmWashId(String mWashId) {
        this.mWashId = mWashId;
    }

    public String getmFeedbackType() {
        return mFeedbackType;
    }

    public void setmFeedbackType(String mFeedbackType) {
        this.mFeedbackType = mFeedbackType;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public Integer getmRating() {
        return mRating;
    }

    public void setmRating(Integer mRating) {
        this.mRating = mRating;
    }
}
