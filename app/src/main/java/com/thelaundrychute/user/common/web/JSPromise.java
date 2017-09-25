package com.thelaundrychute.user.common.web;

import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * promise which is used to call javascript when an Java-Method is completed
 * <p/>
 * Created by timfreiheit on 30.06.15.
 */
public class JSPromise {

    private static final String TAG = JSPromise.class.getSimpleName();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    private WebView mWebView;
    private Callable mCallable;
    private boolean hasValue = false;
    private Object value = null;

    private String mCallbackJavaScript;
    private boolean removeJavaScriptMethodAfterCallback = false;

    public JSPromise(Callable callable) {
        this(null, callable);
    }

    public JSPromise(WebView webView, Callable callable) {
        mCallable = callable;
        mWebView = webView;
        run();
    }

    private void run() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    value = mCallable.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                hasValue = true;
                callback();
            }
        });
    }

    /**
     * returns this
     */
    public JSPromise setWebView(WebView webView) {
        mWebView = webView;
        return this;
    }

    @JavascriptInterface
    public boolean hasValue() {
        return hasValue;
    }

    @JavascriptInterface
    public String getValueAsJson() {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    /**
     * calls javascriptFunctionName when promise is ready
     *
     * @param javascriptFunctionName the function
     */
    @JavascriptInterface
    public void thenJS(String javascriptFunctionName) {
        thenJS(javascriptFunctionName, false);
    }

    /**
     * calls javascriptFunctionName when promise is ready
     *
     * @param javascriptFunctionName    the function
     * @param removeMethodAfterCallback check if method should be removed from window after the callback is done
     *                                  window[javascriptFunctionName] = null
     *                                  this could be needed when the methodname is generated to enable the callback
     */
    @JavascriptInterface
    public void thenJS(String javascriptFunctionName, boolean removeMethodAfterCallback) {
        mCallbackJavaScript = javascriptFunctionName;
        removeJavaScriptMethodAfterCallback = removeMethodAfterCallback;
        if (hasValue) {
            callback();
        }
    }

    private void callback() {
        if (mWebView == null){
            return;
        }
        mWebView.post(new Runnable() {
            @Override
            public void run() {

                if (mCallbackJavaScript != null && mWebView != null) {
                    String js = mCallbackJavaScript + "(" + getValueAsJson() + ");";
                    evaluateJavaScript(js);
                    if (removeJavaScriptMethodAfterCallback) {
                        evaluateJavaScript(String.format("window[%s]=null;", mCallbackJavaScript));
                    }
                }

                //callback done
                mCallbackJavaScript = null;

            }
        });
    }

    private void evaluateJavaScript(String javascript) {
        if (mWebView != null) {
            String js = "javascript:" + javascript;
            if (Build.VERSION.SDK_INT >= 19) {
                mWebView.evaluateJavascript(js, null);
            } else {
                mWebView.loadUrl(js);
            }
        }
    }

}