package com.inMotion.entities.user.profile;

import com.google.gson.annotations.SerializedName;
import com.inMotion.entities.common.Name;
import com.inMotion.entities.common.geography.Address;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class Profile {

    @SerializedName("Name")
    private Name name = null;
    @SerializedName("Address")
    private Address address = null;
    @SerializedName("Telephone")
    private String telephone = null;
    @SerializedName("Device")
    private Device device = null;
    @SerializedName("EmailAddress")
    private String emailAddress = null;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}


/*    public var name:Name?;
    public var address:Address?;
    public var telephone:String?;
    public var emailAddress:String?;

    public var device:Device?;*/