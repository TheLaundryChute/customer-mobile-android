package com.thelaundrychute.user.bag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.inMotion.session.Context;
import com.thelaundrychute.business.bag.functions.finishCycle;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.TLCLoginActivity;
import com.thelaundrychute.user.common.ErrorPopup;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReuseBagActivityFragment extends Fragment {

    private Button mYesButton;
    private Button mNoButton;

    private String bagId;
    private String locationId;

    private ProgressDialog isBusy;

    public static ReuseBagActivityFragment newInstance() {
        return new ReuseBagActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isBusy = new ProgressDialog(getActivity());
        this.isBusy.setTitle(TranslationService.getCurrent().get("Android.Loading.Title", "Processing"));
        this.isBusy.setMessage(TranslationService.getCurrent().get("Android.Loading.SubText", "Please wait"));

        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras == null) {
                this.bagId = null;
                this.locationId = null;
            } else {
                this.bagId = extras.getString("bagId");
                this.locationId = extras.getString("locationId");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reuse_bag, container, false);

        this.mYesButton = (Button) view.findViewById(R.id.yesButton);
        this.mYesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isBusy.show();
                finishTheCycle(true);
            }
        });

        this.mNoButton = (Button) view.findViewById(R.id.noButton);
        this.mNoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isBusy.show();
                finishTheCycle(false);
            }
        });

        return view;
    }

    private void finishTheCycle(Boolean reuse) {

        finishCycle finishCycle = new finishCycle();
        finishCycle.request request = finishCycle.newRequest();
        request.setBagId(this.bagId);
        request.setLocationId(this.locationId);
        request.setDoReactivate(reuse);

        Gson parser = new Gson();
        String result = parser.toJson(request);
        Crashlytics.log(1, "ReuseBagActivityFragment.FinishTheCycle invoked", result);

        finishCycle.execute(request, new INetFunctionDelegate<com.thelaundrychute.business.bag.functions.finishCycle.request, com.thelaundrychute.business.bag.functions.finishCycle.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<finishCycle.request, finishCycle.response> function, Error error) {
                Crashlytics.log(0, "ReuseBagActivityFragment.FinishTheCycle failed", error.getContext());
                cycleFailedToFinish(error.getMessage());
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<finishCycle.request, finishCycle.response> function, FuncResponse<finishCycle.response> result) {
                if (result.isSuccess()) {
                    cycleDidFinish();
                } else {
                    String errorMessage = result.getMessages().get(0);
                    Crashlytics.log(0, "ReuseBagActivityFragment.FinishTheCycle failed", errorMessage);
                    cycleFailedToFinish(errorMessage);
                }
            }
        });
    }


    public void successUserUpdate() {
        getCurrent request = new getCurrent();

//        Gson parser = new Gson();
//        String result = parser.toJson(request);
//        Crashlytics.log(1, "ReuseBagActivityFragment.GetCurrent invoked", result);

        request.execute(null, new INetFunctionDelegate<emptyFuncRequest, getCurrent.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<emptyFuncRequest, getCurrent.response> function, Error error) {
                //Error
                ErrorPopup errorPopup = new ErrorPopup(getActivity(), "Error", error.getMessage(), new ErrorPopup.ErrorPopupDelegate() {
                    @Override
                    public void alertClosed() {
                        Context.getCurrent().logout();
                        Intent intent = new Intent(getActivity(), TLCLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });
                errorPopup.show();
                Crashlytics.log(1, "ReuseBagActivityFragment.GetCurrent failed", error.getContext());


            }

            @Override
            public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                getCurrent.response data = result.getData();
                UserHelper.setUser(data.getUser());
                UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                UserHelper.setActionItem(data.getDetails().getActionItem());

                //Intent intent = WashPagerActivity.newIntent(getActivity(), true);
                Intent intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
                startActivity(intent);
                getActivity().finish();
                isBusy.dismiss();
            }
        });
    }


    private void cycleDidFinish() {
        this.successUserUpdate();
        //isBusy.dismiss();
//
    }

    private void cycleFailedToFinish(String error) {
        isBusy.dismiss();
        ErrorPopup errorPopup = new ErrorPopup(getActivity(), "Error", error, new ErrorPopup.ErrorPopupDelegate() {
            @Override
            public void alertClosed() {
            }
        });
        errorPopup.show();
    }
}
