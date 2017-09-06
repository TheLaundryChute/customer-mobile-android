package com.thelaundrychute.business.translation.functions;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.user.UserBagHistory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public class all extends NetFunction<emptyFuncRequest, all.response> {
    public all() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.TRANSLATION);
    }

    public class response {
        //TODO: This is temporary.  en-US going away.  Dictionary
        @SerializedName("en-US")
        private Map<String, String> translations;

        public Map<String, String> getTranslations() { return translations; }
        public void setTranslations(Map<String, String> translations) { this.translations = translations; }
    }


}