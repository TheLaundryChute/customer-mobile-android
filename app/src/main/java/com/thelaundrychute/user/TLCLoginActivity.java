package com.thelaundrychute.user;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thelaundrychute.user.test.R;
import com.crashlytics.android.Crashlytics;
import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.inMotion.entities.user.User;
import com.inMotion.entities.user.profile.Device;
import com.inMotion.entities.user.profile.IProfileRepositoryDelegate;
import com.inMotion.entities.user.profile.Profile;
import com.inMotion.entities.user.profile.ProfileRepository;
import com.inMotion.session.Context;
import com.inMotion.ui.activities.login.LoginActivity;
import com.thelaundrychute.business.user.UserSettings;
import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.common.GCMHelper;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;


/**
 * A login screen that offers login via email/password.
 */
public class TLCLoginActivity extends LoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button doRegister = (Button) findViewById(R.id.doRegister);
        doRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = WebActivity.newIntent(TLCLoginActivity.this, WebPages.REGISTRATION);
                startActivity(intent);
            }
        });

        Button forgotPassword = (Button) findViewById(R.id.forgotPasswordButton);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.thelaundrychute.com/login/forgot-password"));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_login;
    }

    //TODO:  This is what sets the user's GCM device id.  Not fully tested, but working.  Needs moved
    private void GetGCM(final UserSettings user) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {


                try {
                    GCMHelper  gcmRegistrationHelper = new GCMHelper(getApplicationContext());
                    String projectNumber = getResources().getString(R.string.gcm_project_number);
                    String gcmRegID = gcmRegistrationHelper.GCMRegister(projectNumber);


                    if (user.getProfile() == null || user.getProfile().getDevice() == null ||
                            user.getProfile().getDevice().getId() == null) {
                        //Check to see if the user's Profile has a Device, and if it does, is it recent? If not, refresh it
                        Log.d("login", gcmRegID);
                        //We need to save it to the user profile now
                        Device device = new Device();
                        device.setId(gcmRegID);
                        device.setType("android");
                        device.setVersion(Build.VERSION.RELEASE);
                        device.setAppVersion(device.getAppVersion(TLCLoginActivity.this));

                        user.getProfile().setDevice(device);
                        //TODO: Save the user
                        ProfileRepository profileRepo = new ProfileRepository(new IProfileRepositoryDelegate() {

                            @Override
                            public void profileRepositoryDidFail(ProfileRepository repository, Error error) {
                                Log.d("login", "Failed to save profile.");
                            }

                            @Override
                            public void profileRepositoryProfileNotFound(ProfileRepository repository, User user) {

                                Log.d("login", "Failed to save profile.");
                            }

                            @Override
                            public void profileRepositoryDidGetProfile(ProfileRepository repository, User user, Profile profile) {

                                Log.d("login", "Failed to save profile.");
                            }

                            @Override
                            public void profileRepositoryDidSave(ProfileRepository repository, Profile profile) {
                                Log.d("login", "Saved profile.");
                            }
                        });

                        profileRepo.save(user.getProfile());
                    } else {

                        if (user.getProfile().getDevice() != null && user.getProfile().getDevice().getId() != null) {

                            if (!gcmRegID.contentEquals(user.getProfile().getDevice().getId())) {
                                Device device = new Device();
                                device.setId(gcmRegID);
                                device.setType("android");
                                device.setVersion(Build.VERSION.RELEASE);
                                device.setAppVersion(device.getAppVersion(TLCLoginActivity.this));

                                user.getProfile().setDevice(device);
                                //TODO: Save the user
                                ProfileRepository profileRepo = new ProfileRepository(new IProfileRepositoryDelegate() {

                                    @Override
                                    public void profileRepositoryDidFail(ProfileRepository repository, Error error) {
                                        Log.d("login", "Failed to save profile.");
                                    }

                                    @Override
                                    public void profileRepositoryProfileNotFound(ProfileRepository repository, User user) {

                                        Log.d("login", "Failed to save profile.");
                                    }

                                    @Override
                                    public void profileRepositoryDidGetProfile(ProfileRepository repository, User user, Profile profile) {

                                        Log.d("login", "Failed to save profile.");
                                    }

                                    @Override
                                    public void profileRepositoryDidSave(ProfileRepository repository, Profile profile) {
                                        Log.d("login", "Saved profile.");
                                    }
                                });

                                profileRepo.save(user.getProfile());
                            }


                        }
                    }


                } catch (Exception bug) {
                    bug.printStackTrace();
                }
            }
        });

        thread.start();
    }
    @Override
    public void contextLoginDidSucceed(Context session, User user) {
        //super.contextLoginDidSucceed(session, user);

        /*String authorizedEntity = PROJECT_ID; // Project id from Google Developer Console
        String scope = "GCM"; // e.g. communicating using GCM, but you can use any
                              // URL-safe characters up to a maximum of 1000, or
                              // you can also leave it blank.
        String token = InstanceID.getInstance(context).getToken(authorizedEntity,scope);*/


        //Get current and redirect
        getCurrent request = new getCurrent();

        request.execute(null, new INetFunctionDelegate<emptyFuncRequest, getCurrent.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<emptyFuncRequest, getCurrent.response> function, com.inMotion.core.Error error) {
                //We can't do anything without the current user
                Log.d("login", error.getMessage());
                TLCLoginActivity.this.contextLoginDidFail(Context.getCurrent(), error );
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                getCurrent.response data = result.getData();
                UserHelper.setUser(data.getUser());
                UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                UserHelper.setActionItem(data.getDetails().getActionItem());
                
                Crashlytics.setUserIdentifier(data.getUser().getUserId());
                Crashlytics.setUserEmail(data.getUser().getProfile().getEmailAddress());
                Crashlytics.setUserName(data.getUser().getProfile().getName().toString());

                /*
                Wash wash = UserHelper.getFirstWash();
                long id =  wash != null ? wash.getId() : -1;
                Intent intent = WashPagerActivity.newIntent(TLCLoginActivity.this, id);
                */
                Intent intent = WebActivity.newIntent(TLCLoginActivity.this, WebPages.HOME);
                GetGCM(data.getUser());
                startActivity(intent);
                //Do we want to do this?
                TLCLoginActivity.this.finish();
            }
        });

    }

}

