package com.thelaundrychute.user.common.web;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.inMotion.core.Error;
import com.inMotion.core.config.AppConfig;
import com.inMotion.entities.common.app.AppVersion;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.profile.Device;
import com.inMotion.session.Authorization;
import com.inMotion.session.IContextDelegate;
import com.thelaundrychute.user.MainActivity;
import com.thelaundrychute.user.common.GCMHelper;

import com.thelaundrychute.user.common.fragments.FragmentIntentIntegrator;
import com.thelaundrychute.user.test.R;

import java.util.concurrent.Callable;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class WebFragment extends Fragment implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String ARG_WEB_TARGET = "web_target";
    private CustomWebView mWebView;
    private String mWebTarget;

    private CredentialRequest credentialRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_REQUEST = 11;
    private static Credential credentials;

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
//      TODO: Testing
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
             //   .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        credentialRequest = new CredentialRequest.Builder().setSupportsPasswordLogin(true).build();

//
        com.inMotion.session.Context.init(getActivity());
//        if (com.inMotion.session.Context.getCurrent().getAuthorization() != null) {
//            setHasOptionsMenu(true);
//        }
    }

    private void onCredentialSuccess(Credential credential) {
        //TODO: This is where we can auto login by setting token and putting it into web view
        Toast.makeText(getActivity(), credential.getPassword(), Toast.LENGTH_SHORT).show();
        if (credential.getAccountType() == null) {
            String id = credential.getId();
            String username = credential.getName();
            String password = credential.getPassword();

            Log.d(TAG, "ID: " + id + ", Username: " + username + ", Password: " + password);
            //usernameEditText.setText(username);
            //passwordEditText.setText(password);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
        //String mainUrl = "http://localhost:7161";
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

    public void onReceiveNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("enable();", null);
        } else {
            mWebView.loadUrl("javascript:enable();");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RC_REQUEST) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                onCredentialSuccess(credential);
            } else {
                credentials = null;
            }
        }
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
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
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = data == null || resultCode != getActivity().RESULT_OK ? null : data.getData();
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

    protected class WebLoginDelegate implements IContextDelegate {
        private WebFragment _mFragment;
        private Boolean mIsDone = false;
        private String mToken = null;
        private Authorization mAuthorization = null;

        public WebLoginDelegate(WebFragment fragment) {
            this.mIsDone = false;
            this.mToken = null;
            this.mAuthorization = null;
            this._mFragment = fragment;
        }

        public Boolean isDone() {
            return mIsDone;
        }

        public String getToken() {
            return mToken;
        }

        public Authorization getAuthResponse() {
            return mAuthorization;
        }

        @Override
        public void contextLoginDidFail(com.inMotion.session.Context session, Error error) {
            mToken = null;
            mAuthorization = null;
            mIsDone = true;
        }

        @Override
        public void contextLoginDidSucceed(com.inMotion.session.Context session, User user) {
            mToken = session.getAuthorization().getAccess_token();
            mAuthorization = session.getAuthorization();
            mIsDone = true;
        }

        @Override
        public void contextRefreshWillHappen(com.inMotion.session.Context session) {

        }

        @Override
        public void contextRefreshDidFail(com.inMotion.session.Context session, Error error) {

        }

        @Override
        public void contextRefreshDidSucceed(com.inMotion.session.Context session, User user) {

        }

        @Override
        public void contextUserDidUpdate(com.inMotion.session.Context session, User user) {

        }

        @Override
        public void contextUserWillLogout(com.inMotion.session.Context session, User user) {

        }

        @Override
        public void contextUserDidLogout(com.inMotion.session.Context session, User user) {

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

        @JavascriptInterface
        public JSPromise token() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public String call() throws Exception {

                    final WebLoginDelegate delegate = new WebLoginDelegate(WebFragment.this);
                    Auth.CredentialsApi.request(mGoogleApiClient, credentialRequest).setResultCallback( new ResultCallback<CredentialRequestResult>() {
                        @Override
                        public void onResult(CredentialRequestResult result) {
                            Status status = result.getStatus();
                            if (status.getStatus().isSuccess()) {
                                Log.d(TAG, "GET: OK");
                                credentials = result.getCredential();

                                // GET TOKEN
                                User user = new User(credentials.getName());
                                user.setId(credentials.getId());
                                com.inMotion.session.Context.getCurrent().login(user, credentials.getPassword());
                                com.inMotion.session.Context.getCurrent().addResponder(delegate);
                            } else {
                                delegate.contextLoginDidFail(null, null);
                                if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
                                    try {
                                        status.startResolutionForResult(getActivity(), RC_REQUEST);
                                    } catch (IntentSender.SendIntentException e) {
                                        Toast.makeText(getActivity(), "Save failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
//                    while(WEB_APP == 1) {
//                        Thread.sleep(200);
//                    }
                    while (!delegate.isDone()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    return delegate.getToken();
                }
            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise authenticate(final String username, final String password, final boolean rememberMe) throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public Authorization call() throws Exception {
                    final WebLoginDelegate delegate = new WebLoginDelegate(WebFragment.this);

                    try {
                        //TODO: This method should auth against inmotion and return a token

                        User user = new User(username);
                        user.setId(username);
                        com.inMotion.session.Context.getCurrent().login(user, password);
                        com.inMotion.session.Context.getCurrent().addResponder(delegate);

                    } catch(Exception ex) {

                    }

                    while (!delegate.isDone()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (delegate.getAuthResponse() != null) {
                        Credential credential = new Credential.Builder(username)
                                .setName(username)
                                .setPassword(password)
                                .build();
                        Auth.CredentialsApi.save(mGoogleApiClient, credential).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Log.d(TAG, "SAVE: OK");
                                } else {
                                    resolveResult(status, RC_REQUEST);
                                }
                            }
                        });
                    }
                    return delegate.getAuthResponse();
                }

            }).setWebView(mWebview);
        }

        @JavascriptInterface
        public JSPromise logout() throws Exception {
            return new JSPromise(new Callable() {
                @Override
                public Boolean call() throws Exception {

                    final boolean[] isComplete = {false};
                    final boolean[] isLoggedOut = {false};
                    try {
                        Auth.CredentialsApi.request(mGoogleApiClient, credentialRequest).setResultCallback( new ResultCallback<CredentialRequestResult>() {
                            @Override
                            public void onResult(CredentialRequestResult result) {
                                Status status = result.getStatus();
                                if (status.getStatus().isSuccess()) {
                                    Log.d(TAG, "GET: OK");
                                    credentials = result.getCredential();
                                    Auth.CredentialsApi.delete(mGoogleApiClient, credentials).setResultCallback(new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            if (status.isSuccess()) {
                                                Log.d(TAG, "LOGOUT: OK");
                                                isComplete[0] = true;
                                                isLoggedOut[0] = true;
                                            } else {
                                                Log.d(TAG, "LOGOUT: FAILED");
                                                isComplete[0] = true;
                                                isLoggedOut[0] = false;
                                            }
                                        }
                                    });
                                }
                            }});
                    } catch(Exception ex) {
                        return null;
                    }
                    while (!isComplete[0]) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return isLoggedOut[0];
                }
            }).setWebView(mWebview);
        }

        private void resolveResult(Status status, int requestCode) {
            Log.d(TAG, "Resolving: " + status);
            if (status.hasResolution()) {
                Log.d(TAG, "STATUS: RESOLVING");
                try {
                    status.startResolutionForResult(getActivity(), requestCode);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "STATUS: Failed to send resolution.", e);

                }
            } else {
                Log.e(TAG, "STATUS: FAIL");

            }
        }




    }

}
