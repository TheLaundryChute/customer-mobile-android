package com.thelaundrychute.business.bag;

import com.google.gson.annotations.SerializedName;
import com.thelaundrychute.business.Quantity;
import com.thelaundrychute.business.user.UserBagStatus;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class Bag {
    @SerializedName("Id")
    private String id;
    @SerializedName("Size")
    private Quantity size;
    @SerializedName("IsAvailable")
    private Boolean isAvailable;
    @SerializedName("Status")
    private UserBagStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Quantity getSize() {
        return size;
    }

    public void setSize(Quantity size) {
        this.size = size;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public UserBagStatus getStatus() {
        return status;
    }

    public void setStatus(UserBagStatus status) {
        this.status = status;
    }
}
