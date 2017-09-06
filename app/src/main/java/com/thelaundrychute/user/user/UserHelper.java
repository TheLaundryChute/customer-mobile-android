package com.thelaundrychute.user.user;

import com.thelaundrychute.business.user.UserBag;
import com.thelaundrychute.business.user.UserBagStatus;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.business.user.functions.getCurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class UserHelper {

    private static UserSettings mUser = null;
    private static String mUserMessage = null;
    private static getCurrent.actionItem actionitem = null;


    public static void setUser(UserSettings user) {
        mUser = user;
    }

    public static Wash getFirstWash() {
        List<Wash> activeWashes = getActiveUserWashes();
        if (activeWashes.size() > 0) {
            return activeWashes.get(0);
        }
        return null;
    }

    public static List<Wash> getActiveUserWashes() {
        List<Wash> activeWashes = new ArrayList<Wash>();
        List<Wash> washes = mUser.getWashes();
        if (washes != null) {
            for (Wash wash : washes) {
                String bagStatus = wash.getBag().getStatus().getName();
                if (!bagStatus.equals("INACTIVE")) {
                    activeWashes.add(wash);
                }
            }
        }

        return activeWashes;
    }

    public static Boolean hasAvailableWashes() {
        List<Wash> washes = mUser.getWashes();
        Date today = new Date();
        if (washes != null) {
            for (Wash wash : washes) {
                String bagStatus = wash.getBag().getStatus().getName();
                if (bagStatus.equals("INACTIVE")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Wash getNonWash() {
        Wash wash = new Wash();
        wash.setId(-1);
        UserBag userBag = new UserBag();
        UserBagStatus status = new UserBagStatus();
        status.setName("INACTIVE");
        userBag.setStatus(status);
        wash.setBag(userBag);

        return wash;
    }

    public static UserSettings getUser() {
        if(mUser == null) {
            UserSettings userSettings = new UserSettings();
            //TODO: Do something?
            mUser = userSettings;
        }

        return mUser;
    }

    public static String getUserMessage() { return mUserMessage; }
    public static void setUserMessage(String mUserMessage) { UserHelper.mUserMessage = mUserMessage; }


    public static void setActionItem(getCurrent.actionItem mActionItem) { UserHelper.actionitem = mActionItem; }
    public static getCurrent.actionItem getActionitem() { return actionitem; }

}
