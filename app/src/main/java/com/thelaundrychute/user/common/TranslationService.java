package com.thelaundrychute.user.common;

import android.util.Log;

import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.translation.functions.all;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class TranslationService {
    private Map<String, String> translations = new HashMap<String, String>();

    public TranslationService(Map<String, String> translations) {
        this.translations = translations;
    }

    public String get(String key) {
        return get(key, null, null);
    }

    public String get(String key, String[] args) {
        return get(key, null, args);
    }

    public String get(String key, String defaultValue) {
        return get(key, defaultValue, null);
    }

    //TODO: place holder, need to implement in sdk
    public String get(String key, String defaultValue, String[] args) {
        //TODO: Add logic for args
        //Check if key exists, if it doesn't return default value
        return key != "" && key != null && translations.containsKey(key)? translations.get(key) : defaultValue;
    }

    public static void init() {
        if (current == null){
            padlock.lock();
            if (current == null) {

                all request = new all();
                request.execute(null, new INetFunctionDelegate<emptyFuncRequest, all.response>() {
                    @Override
                    public void netFunctionDidFail(NetFunction<emptyFuncRequest, all.response> function, com.inMotion.core.Error error) {
                        //We can't do anything without the current user
                        Log.d("translation", error.getMessage());
                    }

                    @Override
                    public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, all.response> function, FuncResponse<all.response> result) {
                        all.response response = result.getData();
                        current = new TranslationService(response.getTranslations());
                    }
                });

                padlock.unlock();
            }
        }
    }
    private static Lock padlock = new ReentrantLock();
    private static TranslationService current = null;

    public static TranslationService getCurrent() {
        return current;
    }
}
