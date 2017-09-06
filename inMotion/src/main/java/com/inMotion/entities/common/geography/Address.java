package com.inMotion.entities.common.geography;

import com.google.gson.annotations.SerializedName;
import com.inMotion.entities.common.geography.states.State;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class Address {

    @SerializedName("LineOne")
    private String lineOne = null;
    @SerializedName("LineTwo")
    private String lineTwo = null;
    @SerializedName("City")
    private String city = null;
    @SerializedName("State")
    private State state = null;
    @SerializedName("Zipcode")
    private String zipcode = null;


    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}


/*    public var lineOne:String?;
    public var lineTwo:String?;
    public var city:String?;
    public var state:State?;
    public var zipcode:String?;

    public func toString() -> String {

        var result = "";
        if (self.lineOne != nil) {
            result = self.lineOne! + "\n";
        }

        if (self.lineTwo != nil) {
            result += self.lineTwo! + "\n";
        }

        if (self.city != nil) {
            result += self.city!;
            if (self.state != nil) {
                result += ", " + self.state!.abbreviation! + " ";
            }
        }
        result += self.zipcode!;
        return result;
    }

}*/