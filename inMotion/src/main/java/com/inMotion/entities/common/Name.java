package com.inMotion.entities.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class Name {

    @SerializedName("First")
    private String first;
    @SerializedName("Middle")
    private String middle;
    @SerializedName("Last")
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() { return middle; }

    public void setMiddle(String middle) { this.middle = middle; }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        String firstName = this.getFirst();
        String lastName = this.getLast();
        return firstName + " " + lastName;
    }
}
