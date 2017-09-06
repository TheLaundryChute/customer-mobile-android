package com.thelaundrychute.business.location;

import android.support.annotation.NonNull;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.SerializedName;
import com.thelaundrychute.business.bag.Bag;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ryancoyle on 12/30/15.
 */
public class Bin implements InstanceCreator<Bin> {
    @SerializedName("Id")
    private String id;
//    @SerializedName("Bags")
//    private List<Bag> bags;
//    @SerializedName("MaxCapacity")
//    private String maxCapacity;
//
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//
//    public List<Bag> getBags() {
//        return bags;
//    }
//
//    public void setBags(List<Bag> bags) {
//        this.bags = bags;
//    }
//
//    public String getMaxCapacity() {
//        return maxCapacity;
//    }
//
//    public void setMaxCapacity(String maxCapacity) {
//        this.maxCapacity = maxCapacity;
//    }

    @Override
    public Bin createInstance(Type type) {
        Bin bin = new Bin();
//        bin.bags = new List<Bag>() {
//            @Override
//            public void add(int location, Bag object) {
//
//            }
//
//            @Override
//            public boolean add(Bag object) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(int location, Collection<? extends Bag> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(Collection<? extends Bag> collection) {
//                return false;
//            }
//
//            @Override
//            public void clear() {
//
//            }
//
//            @Override
//            public boolean contains(Object object) {
//                return false;
//            }
//
//            @Override
//            public boolean containsAll(Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public Bag get(int location) {
//                return null;
//            }
//
//            @Override
//            public int indexOf(Object object) {
//                return 0;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @NonNull
//            @Override
//            public Iterator<Bag> iterator() {
//                return null;
//            }
//
//            @Override
//            public int lastIndexOf(Object object) {
//                return 0;
//            }
//
//            @Override
//            public ListIterator<Bag> listIterator() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public ListIterator<Bag> listIterator(int location) {
//                return null;
//            }
//
//            @Override
//            public Bag remove(int location) {
//                return null;
//            }
//
//            @Override
//            public boolean remove(Object object) {
//                return false;
//            }
//
//            @Override
//            public boolean removeAll(Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean retainAll(Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public Bag set(int location, Bag object) {
//                return null;
//            }
//
//            @Override
//            public int size() {
//                return 0;
//            }
//
//            @NonNull
//            @Override
//            public List<Bag> subList(int start, int end) {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public Object[] toArray() {
//                return new Object[0];
//            }
//
//            @NonNull
//            @Override
//            public <T> T[] toArray(T[] array) {
//                return null;
//            }
//        };

        return bin;
    }
}
