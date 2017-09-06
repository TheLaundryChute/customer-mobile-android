package com.thelaundrychute.user.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by KalEl on 1/7/2016.
 */
public class NotificationResponse {
    @SerializedName("Subject")
    private String subject;
    @SerializedName("Content")
    private String content;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
