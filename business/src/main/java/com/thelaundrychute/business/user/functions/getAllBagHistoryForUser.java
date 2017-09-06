package com.thelaundrychute.business.user.functions;

import com.google.gson.reflect.TypeToken;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.Models;
import com.thelaundrychute.business.user.UserBagHistory;

import java.util.ArrayList;

/**
 * Created by Steve Baleno on 12/13/2015.
 */

public class getAllBagHistoryForUser extends NetFunction<emptyFuncRequest, getAllBagHistoryForUser.response> {

    public getAllBagHistoryForUser() {
        super(new TypeToken<FuncResponse<response>>() {}.getType(), Models.USER);
    }

    public class response extends ArrayList<UserBagHistory> {

    }

}
