package com.inMotion.core.objects.serialization;

import com.google.gson.JsonObject;

/**
 * Created by sfbechtold on 9/7/15.
 */
public interface ISerializable {
    JsonObject serialize();
    void willSerialize();
    JsonObject didSerialize(JsonObject result);

    void deserialize(JsonObject value);
    void willDeserialize(JsonObject target);
    void didDeserialize();
}
