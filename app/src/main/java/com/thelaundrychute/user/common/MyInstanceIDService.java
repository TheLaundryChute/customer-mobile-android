package com.thelaundrychute.user.common;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Steve Baleno on 12/16/2015.
 */
public class MyInstanceIDService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        refreshAllTokens();
    }

    private void refreshAllTokens() {
        //TODO: This would be used to refresh tokens in inmotion config
        // assuming you have defined TokenList as
        // some generalized store for your tokens
        /*
        ArrayList<TokenList> tokenList = TokensList.get();
        InstanceID iid = InstanceID.getInstance(this);
        for(tokenItem : tokenList) {
            tokenItem.token =
                    iid.getToken(tokenItem.authorizedEntity,tokenItem.scope,tokenItem.options);
            // send this tokenItem.token to your server
        }*/
    }
};