package com.thelaundrychute.business.location;

import android.support.annotation.NonNull;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class HoursOfOperation implements InstanceCreator<HoursOfOperation>{
    @SerializedName("Label")
    private String label;
    @SerializedName("Days")
    private List<Day> days;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("EndTime")
    private String endTime;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public HoursOfOperation createInstance(Type type) {
        HoursOfOperation hoursOfOperation = new HoursOfOperation();
        hoursOfOperation.days = new List<Day>() {
            @Override
            public void add(int location, Day object) {

            }

            @Override
            public boolean add(Day object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends Day> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Day> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public Day get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Day> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<Day> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Day> listIterator(int location) {
                return null;
            }

            @Override
            public Day remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public Day set(int location, Day object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<Day> subList(int start, int end) {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };

        return hoursOfOperation;
    }
}
