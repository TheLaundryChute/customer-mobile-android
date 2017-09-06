package com.inMotion.core.objects;

import com.google.gson.JsonObject;
import com.inMotion.core.objects.meta.AccessControl;
import com.inMotion.core.objects.meta.Model;
import com.inMotion.core.objects.serialization.Serializable;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class Instance extends Serializable {

    private String id = null;
    private Model model = null;
    private AccessControl acl = null;


    public Instance(String id, Model model, AccessControl acl) {
        this.id = id;
        this.model = model;
        this.acl = acl;
    }

    public static Instance parse(JsonObject value) {
        JsonObject modelValue = value.getAsJsonObject("model");
        Model model = null;
        if (modelValue != null) {
            model = new Model(modelValue.get("id").getAsString(), modelValue.get("name").getAsString());
        }

        AccessControl acl = AccessControl.None;
        Integer aclValue = value.get("acl").getAsInt();
        if (aclValue != null) {
            acl = AccessControl.parse(aclValue);
        }
        return new Instance(
                value.get("guid").getAsString(),
                model,
                acl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public AccessControl getAcl() {
        return acl;
    }

    public void setAcl(AccessControl acl) {
        this.acl = acl;
    }
}
