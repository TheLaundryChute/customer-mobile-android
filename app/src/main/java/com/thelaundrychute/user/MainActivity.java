package com.thelaundrychute.user;

import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.inMotion.entities.common.app.AppVersion;
import com.inMotion.ui.activities.BaseAppCompatActivity;
import com.inMotion.ui.activities.IBaseActivity;
import com.thelaundrychute.business.common.Application;
import com.thelaundrychute.business.common.ApplicationVersion;
import com.thelaundrychute.business.common.ApplicationRepository;
import com.thelaundrychute.business.common.delegates.ApplicationRepositoryDelegate;
import com.thelaundrychute.user.common.UpdateService;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
//import com.thelaundrychute.user.wash.WashPagerActivity;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseAppCompatActivity implements IBaseActivity {

    private NetworkManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

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
            Intent intent = WebActivity.newIntent(MainActivity.this, WebPages.HOME);

            startActivity(intent);
            MainActivity.this.finish();

            NetworkManager.getInstance().start(getApplicationContext());
        }
    }

}