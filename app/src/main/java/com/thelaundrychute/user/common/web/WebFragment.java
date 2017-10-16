package com.thelaundrychute.user.common.web;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.inMotion.core.config.AppConfig;
import com.inMotion.entities.common.app.AppVersion;
import com.inMotion.entities.user.profile.Device;
import com.thelaundrychute.user.MainActivity;
import com.thelaundrychute.user.common.GCMHelper;

import com.thelaundrychute.user.common.fragments.FragmentIntentIntegrator;
import com.thelaundrychute.user.test.R;

import java.util.concurrent.Callable;

public class WebFragment extends Fragment {
    public static final String ARG_WEB_TARGET = "web_target";
    private CustomWebView mWebView;
    private String mWebTarget;

    private static String qr_data = "";
    public static int WEB_APP = 0;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private ProgressDialog isBusy;


    public static WebFragment newInstance(String webTarget) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEB_TARGET, webTarget);

        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isBusy = new ProgressDialog(getActivity());
        this.isBusy.setTitle("Processing");
        this.isBusy.setMessage("Please wait");
        mWebTarget = (String) getArguments().getSerializable(ARG_WEB_TARGET);
//
//        com.inMotion.session.Context.init(getActivity());
//        if (com.inMotion.session.Context.getCurrent().getAuthorization() != null) {
//            setHasOptionsMenu(true);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
		final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    
                });
        mWebView = (CustomWebView) view.findViewById(R.id.common_webview);
		mWebView.setGestureDetector(gesture);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            Handler handler = new Handler();

            int numberOfTaps = 0;
            long lastTapTimeMs = 0;
            long touchDownMs = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        //handler.removeCallbacksAndMessages(null);

                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap

                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                            break;
                        }

                        if (numberOfTaps > 0
                                && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }

                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 5) {
                            if (applicationIsLoaded) {
                                loadTroubleshoot();
                            } else {
                                loadApplication();
                            }
                            Toast.makeText(getActivity(), "Application Switch", Toast.LENGTH_SHORT).show();
                            //handle triple tap
                        }
                }

                return false;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if (getActivity() != null && title != null && !TextUtils.isEmpty(title) && !title.contains(".com") &&
                     !title.contains(".") &&!title.contains("The Laundry Chute")) {
                    getActivity().setTitle(title);
                }
            }

            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        // HTML5 API flags
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        JavaScriptInterface jsInterface = new JavaScriptInterface(getActivity(), mWebView);
        mWebView.addJavascriptInterface(jsInterface, "AndroidInterface");

        loadApplication();

        return view;
    }

    private boolean applicationIsLoaded = false;
    private void loadApplication() {
        String token = "";
        if (com.inMotion.session.Context.getCurrent().getAuthorization() != null) {
            token = com.inMotion.session.Context.getCurrent().getAuthorization().getAccess_token();
        }
        String mainUrl = AppConfig.getCurrent().getNetwork().getWeb().toString() + mWebTarget;
        //String mainUrl = "http://10.0.3.2:7161";
        String uri = Uri.parse(mainUrl)
                .buildUpon()
                .appendQueryParameter("isWebView", "true")
                .appendQueryParameter("token", token)
                .build().toString();
        //
        mWebView.loadUrl(uri);
        applicationIsLoaded = true;
    }

    private void loadTroubleshoot() {
        String uri = "file:///android_asset/troubleshoot/index.html";
        mWebView.loadUrl(uri);
        applicationIsLoaded = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult.getContents() == null) {
            qr_data = null;
        } else {

            Uri uri = Uri.parse(scanResult.getContents());
            qr_data = uri.getQueryParameter("bagId");
        }
        // HACK: This is used by javascript interface call back
        WEB_APP = 0;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != getActivity().RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(getActivity().getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    public class JavaScriptInterface {
        Context mContext;
        WebView mWebview;

        public JavaScriptInterface(Context c, WebView webview) {
            mWebview = webview;
            mContext = c;
        }

        @JavascriptInterface
        public JSPromise getDeviceId() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public String call() throws Exception {
                    try {
                        GCMHelper gcmRegistrationHelper = new GCMHelper(mContext.getApplicationContext());
                        String projectNumber = getResources().getString(R.string.gcm_project_number);
                        String gcmRegID = gcmRegistrationHelper.GCMRegister(projectNumber);

                        return gcmRegID;
                    } catch(Exception ex) {
                        return null;
                    }
                }
            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise getApplicationVersion() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public String call() throws Exception {
                    return AppVersion.appVersion(mContext);
                }
            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise activateScanner() {
            return new JSPromise(new Callable() {
                @Override
                public String call() throws Exception {
                    WEB_APP = 1;
                    try {
                        IntentIntegrator integrator = new FragmentIntentIntegrator(WebFragment.this);
                        integrator.initiateScan();
                    } catch(Exception ex) {
                        Toast.makeText(mContext, "error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    while(WEB_APP == 1) {
                        Thread.sleep(200);
                    }

                     return qr_data;
                }
            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise refreshApplication() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public String call() throws Exception {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();

                    return "success";
                }
            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise getDeviceInfo() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public Device call() throws Exception {
                    try {
                        GCMHelper gcmRegistrationHelper = new GCMHelper(mContext.getApplicationContext());
                        String projectNumber = getResources().getString(R.string.gcm_project_number);
                        String gcmRegID = gcmRegistrationHelper.GCMRegister(projectNumber);

                        Device device = new Device();
                        device.setId(gcmRegID);
                        device.setType("android");
                        device.setVersion(Build.VERSION.RELEASE);
                        device.setAppVersion(AppVersion.appVersion(mContext));

                        return device;
                    } catch(Exception ex) {
                        return null;
                    }
                }
            }).setWebView(mWebview);
        }



    }

}
