package com.thelaundrychute.user.common.web;

/**
 * Created by Steve Baleno on 12/14/2015.
 */
public final class WebPages {
    public static final String HOOK_INDICATOR = "tic://";
    public static final String HOME = "";
    public static final String MESSAGES = "messages";
    public static final String PROFILE = "settings/profile";
    public static final String HISTORY = "wash-history";
    public static final String FAQ = "faq";

    public static final String SETTINGS_WASH = "settings/wash";

    public static final String FEEDBACK = "wash/feedback/add";
    public static final String CUSTOMIZE = "wash/customize";
    public static final String PREFERENCES = "settings/wash";
    public static final String BUY_PLAN = "/plan/select";
    public static final String DROP_OFF = "wash/confirm";

    public static final String LOCATIONS_DROPOFF = "locations/dropoff";
    public static final String LOCATIONS_PICKUP = "locations/pickup";
    public static final String LOCATIONS_BAG_PICKUP = "locations/bagPickup";

    public static final String REGISTRATION = "login/tutorial/howItWorks";

    public static boolean isWebHook(String url) {
        return url.startsWith(HOOK_INDICATOR);
    }
}
