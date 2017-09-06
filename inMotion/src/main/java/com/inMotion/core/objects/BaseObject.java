package com.inMotion.core.objects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.inMotion.core.objects.serialization.Serializable;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class BaseObject extends Serializable {

    private Instance instance = null;
    private JsonObject raw = null;
    private Boolean wasInited = false;

    public void init(Instance instance, JsonObject raw) {
        if (this.wasInited) {
            return; // TODO: Need an error here.
        }

        this.instance = instance;
        this.raw = raw;
        this.wasInited = true;
    }


    @Override
    public JsonObject didSerialize(JsonObject result) {
        result.remove("wasInited");
        result.remove("instance");
        result.remove("raw");

        return super.didSerialize(result);
    }

    public Instance getInstance() {
        return instance;
    }
}
