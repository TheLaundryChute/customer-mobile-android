package com.inMotion.core.objects.serialization;

import com.google.gson.JsonObject;

/**
 * Created by sfbechtold on 9/7/15.
 */
public class Serializable implements ISerializable {

    public JsonObject serialize() {
        this.willSerialize();

        return this.didSerialize(null);
    }

    public void deserialize(JsonObject value) {
        this.willDeserialize(value);

        this.didDeserialize();
    }


    public void willSerialize() {

    }

    public JsonObject didSerialize(JsonObject result) {

        return result;
    }

    public void willDeserialize(JsonObject result) {

    }

    public void didDeserialize() {

    }

}