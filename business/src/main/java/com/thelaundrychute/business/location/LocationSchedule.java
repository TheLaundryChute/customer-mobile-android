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
 * Created by ryancoyle on 12/30/15.
 */
public class LocationSchedule implements InstanceCreator<LocationSchedule> {
    @SerializedName("Types")
    private List<LocationType>types;
    @SerializedName("HoursOfOperation")
    private List<HoursOfOperation> hoursofOperation;
    @SerializedName("SpecialDays")
    private List<SpecialDays>specialDays;

    public List<LocationType> getTypes() {
        return types;
    }

    public void setTypes(List<LocationType> types) {
        this.types = types;
    }

    public List<HoursOfOperation> getHoursofOperation() {
        return hoursofOperation;
    }

    public void setHoursofOperation(List<HoursOfOperation> hoursofOperation) {
        this.hoursofOperation = hoursofOperation;
    }

    public List<SpecialDays> getSpecialDays() {
        return specialDays;
    }

    public void setSpecialDays(List<SpecialDays> specialDays) {
        this.specialDays = specialDays;
    }


    @Override
    public LocationSchedule createInstance(Type type) {
        LocationSchedule schedule = new LocationSchedule();
        schedule.types = new List<LocationType>() {
            @Override
            public void add(int location, LocationType object) {

            }

            @Override
            public boolean add(LocationType object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends LocationType> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends LocationType> collection) {
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
            public LocationType get(int location) {
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
            public Iterator<LocationType> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<LocationType> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<LocationType> listIterator(int location) {
                return null;
            }

            @Override
            public LocationType remove(int location) {
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
            public LocationType set(int location, LocationType object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<LocationType> subList(int start, int end) {
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
        schedule.hoursofOperation = new List<HoursOfOperation>() {
            @Override
            public void add(int location, HoursOfOperation object) {

            }

            @Override
            public boolean add(HoursOfOperation object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends HoursOfOperation> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends HoursOfOperation> collection) {
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
            public HoursOfOperation get(int location) {
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
            public Iterator<HoursOfOperation> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<HoursOfOperation> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<HoursOfOperation> listIterator(int location) {
                return null;
            }

            @Override
            public HoursOfOperation remove(int location) {
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
            public HoursOfOperation set(int location, HoursOfOperation object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<HoursOfOperation> subList(int start, int end) {
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
        schedule.specialDays = new List<SpecialDays>() {
            @Override
            public void add(int location, SpecialDays object) {

            }

            @Override
            public boolean add(SpecialDays object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends SpecialDays> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends SpecialDays> collection) {
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
            public SpecialDays get(int location) {
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
            public Iterator<SpecialDays> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<SpecialDays> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<SpecialDays> listIterator(int location) {
                return null;
            }

            @Override
            public SpecialDays remove(int location) {
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
            public SpecialDays set(int location, SpecialDays object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<SpecialDays> subList(int start, int end) {
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

        return schedule;
    }
}
