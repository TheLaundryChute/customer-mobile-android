package com.thelaundrychute.user.common.web;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.inMotion.core.Error;
import com.inMotion.core.config.AppConfig;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.bag.BagScanActivity;
import com.thelaundrychute.user.bag.BagScanFragment;
import com.thelaundrychute.user.bag.BagScanType;
import com.thelaundrychute.user.common.ErrorPopup;
import com.thelaundrychute.user.common.PinAlertDialog;

import com.thelaundrychute.user.common.fragments.FragmentIntentIntegrator;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.TLCLoginActivity;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;

public class WebFragment extends Fragment {
    public static final String ARG_WEB_TARGET = "web_target";
    private CustomWebView mWebView;
    private String mWebTarget;

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
        TranslationService t = TranslationService.getCurrent();
        this.isBusy = new ProgressDialog(getActivity());
        this.isBusy.setTitle("Processing");
        this.isBusy.setMessage("Please wait");
        mWebTarget = (String) getArguments().getSerializable(ARG_WEB_TARGET);

        com.inMotion.session.Context.init(getActivity());
        if (com.inMotion.session.Context.getCurrent().getAuthorization() != null) {
            setHasOptionsMenu(true);
        }

        this.validateUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: We are going to need parameters here specific to a web view and what to render

        View view = inflater.inflate(R.layout.fragment_web, container, false);

        //TODO: refactor to custom web view / client
        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        //Log.i(Constants.APP_TAG, "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                mWebView.goForward();
                                // Log.i(Constants.APP_TAG, "Right to Left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                getActivity().onBackPressed();
                                //Log.i(SyncStateContract.Constants.APP_TAG, "Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });


        mWebView = (CustomWebView) view.findViewById(R.id.common_webview);
        mWebView.setGestureDetector(gesture);

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

        mWebView.getSettings().setJavaScriptEnabled(true);
        // HTML5 API flags
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);

        JavaScriptInterface jsInterface = new JavaScriptInterface(getActivity());
        mWebView.addJavascriptInterface(jsInterface, "TLCAndroidInterface");


        String token = "";
        if (com.inMotion.session.Context.getCurrent().getAuthorization() != null) {
            token = com.inMotion.session.Context.getCurrent().getAuthorization().getAccess_token();
        }

        //String mainUrl = "http://mobileweb.thelaundrychute.com/webview/" + mWebTarget;
        String mainUrl = AppConfig.getCurrent().getNetwork().getWeb().toString() + mWebTarget;


        String uri = Uri.parse(mainUrl)
                .buildUpon()
                        //.appendPath(mWebTarget)
                .appendQueryParameter("isWebView", "true")
                .appendQueryParameter("token", token)
                .build().toString();
        mWebView.loadUrl(uri);
        //mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
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

    private void validateUser() {

        if (mWebTarget.contentEquals(WebPages.CUSTOMIZE) || mWebTarget.contentEquals(WebPages.BUY_PLAN) || mWebTarget.contentEquals(WebPages.PROFILE) || mWebTarget.contentEquals(WebPages.FEEDBACK)) {

            final PinAlertDialog alert = new PinAlertDialog(this.getActivity(), UserHelper.getUser().getPin(), new PinAlertDialog.PinAlertDialogDelegate() {
                @Override
                public void pinEntryValidated() {
                }

                @Override
                public void pinEntryCanceled() {
                    getActivity().finish();
                }
            });
        }





    }

    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    public void successUserUpdate() {
        getCurrent request = new getCurrent();

        request.execute(null, new INetFunctionDelegate<emptyFuncRequest, getCurrent.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<emptyFuncRequest, getCurrent.response> function, Error error) {
                //Error
                ErrorPopup errorPopup = new ErrorPopup(getActivity(), "Error", error.getMessage(), new ErrorPopup.ErrorPopupDelegate() {
                    @Override
                    public void alertClosed() {
                        com.inMotion.session.Context.getCurrent().logout();
                        Intent intent = new Intent(getActivity(), TLCLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });
                errorPopup.show();

                Crashlytics.log(0, "BagScanFragment.getCurrent failed", error.getContext());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().finish();

                    }
                });

            }

            @Override
            public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                getCurrent.response data = result.getData();
                UserHelper.setUser(data.getUser());
                UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                UserHelper.setActionItem(data.getDetails().getActionItem());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isBusy.dismiss();
                       // Intent intent = WashPagerActivity.newIntent(getActivity(), true);
                        Intent intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public class JavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void loginActivity(String emailAddress)
        {
            Intent i = new Intent(getActivity(), TLCLoginActivity.class);
            startActivity(i);
            getActivity().finish();
        }

        @JavascriptInterface
        public void dropOffScan(String bagId) {
            // Called when a user select continue on the confirm wash page. All that is needed is the bagId).

            Intent intent = BagScanActivity.newIntent(getActivity(), BagScanType.DROP_OFF_BAG, bagId, null);
            startActivity(intent);
            getActivity().finish();
        }

        @JavascriptInterface
        public void closeActivity() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isBusy.show();
                }
            });
            successUserUpdate();
        }

        @JavascriptInterface
        public String activateScanner(String bagId) {
            // Called when a user select continue on the confirm wash page. All that is needed is the bagId).

            //Intent intent = BagScanActivity.newIntent(getActivity(), BagScanType.DROP_OFF_BAG, bagId, null);
           // startActivity(intent);
            //getActivity().finish();

            BagScanFragment scanFragment = new BagScanFragment();
            IntentIntegrator integrator = new FragmentIntentIntegrator(scanFragment);
            integrator.initiateScan();
            return "";

        }

        @JavascriptInterface
        public void onLogin(String bagId) {
            // TODO: This needs to invoke the login to register the device's id for notifications

        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult.getContents() == null) {
                //Error handling here
                ErrorPopup error = new ErrorPopup(getActivity(), "Error", "Didn't get the code", new ErrorPopup.ErrorPopupDelegate() {
                    @Override
                    public void alertClosed() {
                    }
                });
                error.show();

                return;
            }


            // This is all from bag scan fragment

            Uri uri = Uri.parse(scanResult.getContents());
            String bagId = uri.getQueryParameter("bagId");
            String binId = uri.getQueryParameter("binId");

        }
    }

}
