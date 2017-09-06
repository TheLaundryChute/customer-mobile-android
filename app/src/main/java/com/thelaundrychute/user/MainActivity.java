package com.thelaundrychute.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.*;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.inMotion.entities.common.app.AppVersion;
import com.inMotion.ui.activities.BaseAppCompatActivity;
import com.inMotion.ui.activities.IBaseActivity;
import com.thelaundrychute.business.common.Application;
import com.thelaundrychute.business.common.ApplicationVersion;
import com.thelaundrychute.business.common.ApplicationRepository;
import com.thelaundrychute.business.common.delegates.ApplicationRepositoryDelegate;
import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.common.UpdateService;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseAppCompatActivity implements IBaseActivity {

    private NetworkManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());


        //TODO: this needs moved to happen before app loads
        TranslationService.init();

        com.inMotion.session.Context.init(this);

        //Check to see if the application is obsolete.  If so, redirect the user to the upgrade screen
        ApplicationRepository appRepo = new ApplicationRepository(new ApplicationRepositoryDelegate() {
            @Override
            public void repositoryDidGetMany(ApplicationRepository repository, com.inMotion.core.objects.lists.List<Application> results) {
                Application application = null;
                ApplicationVersion applicationVersion = null;
                Class<?> enclosingClass = getClass().getEnclosingClass();
                String packageName = "";
                if (enclosingClass != null) {
                    packageName = enclosingClass.getPackage().getName();
                } else {
                    packageName = getClass().getPackage().getName();
                }
                for (Application app:  results.getRecords()) {
                    if (app.getName().equals(packageName)) {
                        application = app;
                    }
                }

                for(ApplicationVersion appVersion: application.getVersions()) {
                    if (appVersion.getRevision().equals(AppVersion.appVersion(MainActivity.this)))  {
                        applicationVersion = appVersion;
                    }
                }

                if (applicationVersion != null && applicationVersion.getObsolete()) {
                    Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
                    startActivity(intent);
                }


            }
        });

        appRepo.all(10, 0, null);

        if(!UpdateService.isAppCurrent()) {
            Intent intent = new Intent(this,UpgradeActivity.class);
            startActivity(intent);
        }else {
            Log.d("else", "failure");


            Boolean isLoggedIn = com.inMotion.session.Context.getCurrent().getUser() != null;
            if (isLoggedIn) {
                getCurrent getCurrentUserRequest = new getCurrent();

                getCurrentUserRequest.execute(null, new INetFunctionDelegate<emptyFuncRequest, getCurrent.response>() {
                    @Override
                    public void netFunctionDidFail(NetFunction<emptyFuncRequest, getCurrent.response> function, com.inMotion.core.Error error) {
                        //We can't do anything without the current user
                        Log.d("login", error.getMessage());
                    }

                    @Override
                    public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                        getCurrent.response data = result.getData();
                        UserHelper.setUser(data.getUser());
                        UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                        UserHelper.setActionItem(data.getDetails().getActionItem());
                        /*
                        List<Wash> washes = UserHelper.getActiveUserWashes();
                        long washId = -1;
                        if (washes.size() > 0) {
                            washId = washes.get(0).getId();
                        }

                        Intent intent = WashPagerActivity.newIntent(MainActivity.this, washId);*/
                        Intent intent = WebActivity.newIntent(MainActivity.this, WebPages.HOME);

                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });

            } else {
                Intent i = new Intent(MainActivity.this, TLCLoginActivity.class);
                startActivity(i);
                this.finish();
            }

            NetworkManager.getInstance().start(getApplicationContext());
        }
    }

    @Override
    public boolean getRequiresUser() {
        return false;
    }
}