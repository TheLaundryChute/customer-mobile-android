package com.thelaundrychute.user.common.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class CustomWebViewClient extends WebViewClient {
    private ProgressDialog mProgressDialog;

    private Context mContext;
    public CustomWebViewClient(AssetManager am){
        this.am = am;
    }
    AssetManager am;

    @Override
    public WebResourceResponse shouldInterceptRequest (WebView view, String url){
        if(url.indexOf("file:///android_asset") == 0 && url.contains("?")){
            String filePath = url.substring(22, url.length());
            filePath = filePath.substring(0, filePath.indexOf("?"));
            try {
                InputStream is = am.open(filePath);
                WebResourceResponse wr = new WebResourceResponse("text/javascript", "UTF-8", is);
                return wr;
            } catch (IOException e) {
                return null;
            }
        }else{
            return null;
        }
    }

    private void animate(final WebView view) {
        //Works but looks funky
        Animation anim = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        view.startAnimation(anim);
    }

}
