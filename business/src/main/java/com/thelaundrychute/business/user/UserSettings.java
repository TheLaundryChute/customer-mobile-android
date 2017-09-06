package com.thelaundrychute.business.user;

import com.google.gson.annotations.SerializedName;
import com.inMotion.core.objects.BaseObject;
import com.inMotion.entities.user.profile.Profile;

import java.util.List;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class UserSettings extends BaseObject {
    @SerializedName("UserId")
    private String mUserId;
    @SerializedName("Profile")
    private Profile mProfile;
    @SerializedName("Pin")
    private String mPin;
    @SerializedName("StudentId")
    private String mStudentId;
    @SerializedName("Gender")
    private String mGender;

    @SerializedName("Washes")
    private List<Wash> mWashes;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public Profile getProfile() {
        return mProfile;
    }

    public void setProfile(Profile mProfile) {
        this.mProfile = mProfile;
    }

    public String getPin() {
        return mPin;
    }

    public void setPin(String mPin) {
        this.mPin = mPin;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String mStudentId) {
        this.mStudentId = mStudentId;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public List<Wash> getWashes() {
        return mWashes;
    }

    public void setWashes(List<Wash> mWashes) {
        this.mWashes = mWashes;
    }
}
