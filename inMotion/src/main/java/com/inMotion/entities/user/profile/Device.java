package com.inMotion.entities.user.profile;

import com.google.gson.annotations.SerializedName;
import com.inMotion.entities.common.app.AppVersion;
import android.content.Context;

/**
 * Created by sfbechtold on 12/2/15.
 */
public class Device {
    @SerializedName("Id")
    private String id = null;
    @SerializedName("Type")
    private String type = null;
    @SerializedName("Version")
    private String version = null;
    @SerializedName("AppVersion")
    private String appVersion = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion(){
        return version;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion(Context context){
        return AppVersion.appVersion(context);
    }

    public void setVersion(String version){
        this.version = version;
    }
}
