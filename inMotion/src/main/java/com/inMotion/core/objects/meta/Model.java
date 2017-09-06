package com.inMotion.core.objects.meta;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class Model {

    private String id = null;
    private String name = null;

    public Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
