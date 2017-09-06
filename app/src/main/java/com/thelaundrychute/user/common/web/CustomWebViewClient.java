package com.thelaundrychute.user.common.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class CustomWebViewClient extends WebChromeClient {
    private ProgressDialog mProgressDialog;

    private Context mContext;
    public CustomWebViewClient(Context context, ProgressDialog progressDialog){
        mContext = context;
        mProgressDialog = progressDialog;
    }


    private void animate(final WebView view) {
        //Works but looks funky
        Animation anim = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        view.startAnimation(anim);
    }

}
