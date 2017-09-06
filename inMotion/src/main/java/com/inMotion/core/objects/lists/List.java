package com.inMotion.core.objects.lists;

import com.inMotion.core.objects.BaseObject;


/**
 * Created by sfbechtold on 12/9/15.
 */
public class List<T extends BaseObject> {

    private Pager pager = null;
    private java.util.List<T> records = null;

    public List(Pager pager, java.util.List<T> records) {
        this.pager = pager;
        this.records = records;
    }

    public Pager getPager() {
        return pager;
    }

    public java.util.List<T> getRecords() {
        return records;
    }
}
