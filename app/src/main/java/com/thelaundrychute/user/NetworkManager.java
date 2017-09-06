package com.thelaundrychute.user;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;


/**
 * Created by ryancoyle on 1/7/16.
 */
public class NetworkManager {

    private Intent mNoInternetIntent;
    private Activity mNoInternetActivity;
    private Boolean mIsRunning = false;
    private Boolean mNoInternet;
    private Context mContext;
    private Handler mainThreadHandler;


    protected NetworkManager() {
    }

    private volatile static NetworkManager current = null;

    public static NetworkManager getInstance() {
        if (current == null) {
            synchronized (NetworkManager.class) {
                if (current == null) {
                    current = new NetworkManager();
                }
            }
        }
        return current;
    }

    public Boolean start(Context context) {

        if (this.mIsRunning) {
            return false;
        }
        if (this.mContext == null) {
            this.mContext = context;
        }

        this.mIsRunning = true;
        this.mNoInternet = false;

        mainThreadHandler = new Handler(mContext.getMainLooper());
        Thread backgroundThread = new Thread() {
            @Override
            public void run() {
                while(mIsRunning) {

                    if (!isConnected(mContext)) {

                        if (!mNoInternet) {
                            mNoInternet = true;

                            //Display intent on main thread
                            Runnable displayAlert = new Runnable() {

                                @Override
                                public void run() {
                                    if (mNoInternetIntent == null) {
                                        mNoInternetIntent = new Intent(mContext, NoInternetActivity.class);
                                        mNoInternetIntent.setAction(NoInternetActivity.class.getName());
                                        mNoInternetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    }
                                    mContext.startActivity(mNoInternetIntent);
                                }
                            };
                            mainThreadHandler.post(displayAlert);
                        }


                    } else {
                        if (mNoInternet) {
                            mNoInternet = false;

                            //Close intent on main thread
                            Runnable removeAlert = new Runnable() {

                                @Override
                                public void run() {
                                    if (mNoInternetActivity != null) {
                                        mNoInternetActivity.finish();
                                    }
                                }
                            };
                            mainThreadHandler.post(removeAlert);
                        }
                    }
                }
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        backgroundThread.start();

        return true;
    }

    public void setActivity(Activity activity) {
        this.mNoInternetActivity = activity;
    }

    public void stop() {
        this.mIsRunning = false;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
