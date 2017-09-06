package com.inMotion.core.objects.lists;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class Path {

    private Integer index = null;
    private Integer length = null;

    public Path(Integer index, Integer length) {
        this.index = index;
        this.length = length;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getLength() {
        return length;
    }
}
