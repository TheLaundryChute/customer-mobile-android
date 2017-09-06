package com.thelaundrychute.user.bag;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.inMotion.session.Context;
import com.thelaundrychute.business.bag.functions.activate;
import com.thelaundrychute.business.bag.functions.dropoff;
import com.thelaundrychute.business.bag.functions.pickup;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.TLCLoginActivity;
import com.thelaundrychute.user.common.ErrorPopup;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.common.fragments.FragmentIntentIntegrator;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;


public class BagScanFragment extends Fragment {
    TranslationService t;
    private Fragment _mCurrentFragment;
    private TextView _mInstructionsTextView;
    private TextView _mainTextView;
    private TextView _subTextView;
    private ImageView _accentImageView;
    private ImageView _arrowImageView;
    private LinearLayout _buttonContainer;
    private Button mButton;

    private String bagId;
    private String binId;
    private String pickupLocation;
    private Long washId;

    private BagScanType scanType;
    private Boolean hasError;

    private ProgressDialog isBusy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = TranslationService.getCurrent();
        this.isBusy = new ProgressDialog(getActivity());
        this.isBusy.setTitle(t.get("Android.Loading.Title", "Processing"));
        this.isBusy.setMessage(t.get("Android.Loading.SubText", "Please wait"));
        setHasOptionsMenu(true);


        _mCurrentFragment = this;
        //Get the bag id from the activity
        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras == null) {
                this.bagId = null;
                this.scanType = BagScanType.ACTIVATE;
            } else {
                this.bagId = extras.getString("bagId");
                this.scanType = (BagScanType) extras.get("scanType");
                this.pickupLocation = extras.getString("locationId");
            }
        }
        this.hasError = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bag_scan, container, false);
        this._mInstructionsTextView = (TextView) view.findViewById(R.id.instructionsTextView);
        this._mainTextView = (TextView) view.findViewById(R.id.mainTextView);
        this._subTextView = (TextView) view.findViewById(R.id.subTextView);
        this._accentImageView = (ImageView) view.findViewById(R.id.accentImageView);
        this._arrowImageView = (ImageView) view.findViewById(R.id.arrowImageView);
        this._buttonContainer = (LinearLayout) view.findViewById(R.id.messageContainer);

        // Scanner
        this.setupInterface();

        mButton = (Button) view.findViewById(R.id.scanButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanType == BagScanType.DROP_OFF_BIN) {
                    _buttonContainer.setVisibility(View.INVISIBLE);
                }
                IntentIntegrator integrator = new FragmentIntentIntegrator(_mCurrentFragment);
                integrator.initiateScan();
            }
        });
        mButton.setText(TranslationService.getCurrent().get("Scanner.Android.ScanBag", "Scan Bag"));

        return view;
    }

    private void setupInterface() {
        switch(this.scanType) {
            case ACTIVATE:
                this._mInstructionsTextView.setText(TranslationService.getCurrent().get("Scanner.Activate.Instructions"));

                break;
            case DROP_OFF_BAG:
                this._mInstructionsTextView.setText(TranslationService.getCurrent().get("Scanner.DropOff.Instructions"));

                break;
            case DROP_OFF_BIN:
                this._mInstructionsTextView.setText(TranslationService.getCurrent().get("Scanner.ScanBin.Instructions"));

                break;
            case PICK_UP:
                this._mInstructionsTextView.setText(TranslationService.getCurrent().get("Scanner.PickUp.Instructions"));

                break;
        }

        this._buttonContainer.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                switch(scanType) {
                    case DROP_OFF_BAG:
                        break;
                    case ACTIVATE:
                        isBusy.show();
                        intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
                        startActivity(intent);
                        getActivity().finish();
                        isBusy.dismiss();
                        break;
                    case DROP_OFF_BIN:
                        if (!hasError) {
                            isBusy.show();

                            /*if (washId != null) {
                                intent = WashPagerActivity.newIntent(getActivity(), washId);
                            } else {
                                intent = WashPagerActivity.newIntent(getActivity(), bagId);
                            }*/
                            /*if (washId != null) {
                                intent = WebActivity.newIntent(getActivity(), WebPages.HOME, "/bag-list/", washId.toString());
                            }else {
                                intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
                            }*/
                            intent = WebActivity.newIntent(getActivity(), WebPages.HOME);

                            startActivity(intent);
                            getActivity().finish();
                            isBusy.dismiss();
                        }
                        break;

                    case PICK_UP:
                        //Need to add review modal/ finish cycle
                        isBusy.show();
                        //Intent intent = WashPagerActivity.newIntent(getActivity(), washId);
                        //Intent intent = WebActivity.newIntent(getActivity(), WebPages.HOME, "/bag-list/", washId.toString());
                        intent = WebActivity.newIntent(getActivity(), WebPages.HOME);
                        startActivity(intent);
                        getActivity().finish();
                        isBusy.dismiss();
                        break;
                }


            }
        });
    }

    private void setButtonParameters(String main, String sub, int accentImage, boolean arrow, String colorHex) {
        this._mainTextView.setText(main);
        this._subTextView.setText(sub);
        this._accentImageView.setImageResource(accentImage);
        if (arrow) {
            this._arrowImageView.setVisibility(View.VISIBLE);
        } else {
            this._arrowImageView.setVisibility(View.GONE);
        }
        this._buttonContainer.setBackgroundColor(Color.parseColor(colorHex));
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
                        Context.getCurrent().logout();
                        Intent intent = new Intent(getActivity(), TLCLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });
                errorPopup.show();
                Crashlytics.log(0, "BagScanFragment.getCurrent failed", error.getContext());

            }

            @Override
            public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                getCurrent.response data = result.getData();
                UserHelper.setUser(data.getUser());
                UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                UserHelper.setActionItem(data.getDetails().getActionItem());

                _buttonContainer.setVisibility(View.VISIBLE);
                isBusy.dismiss();
            }
        });
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
        Uri uri = Uri.parse(scanResult.getContents());
        String bagId = uri.getQueryParameter("bagId");
        String binId = uri.getQueryParameter("binId");
        mButton.setVisibility(View.INVISIBLE);




        switch (this.scanType) {
            case DROP_OFF_BAG:{
                if (bagId == null) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.InvalidBag"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);

                    return;
                } else if (!bagId.contentEquals(this.bagId)) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.BagMismatch"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);

                    return;
                }

                this._buttonContainer.setVisibility(View.VISIBLE);
                this.setButtonParameters(TranslationService.getCurrent().get("Scanner.DropOff.Button.Success.MainText"),
                        TranslationService.getCurrent().get("Scanner.Android.DropOff.Button.Success.SubText"),
                        R.drawable.star,
                        false,
                        "#7AC93B");
                this.scanType = BagScanType.DROP_OFF_BIN;
                this.setupInterface();
                this.hasError = true; //Hack
                mButton.setVisibility(View.VISIBLE);
                mButton.setText(TranslationService.getCurrent().get("Scanner.Android.ScanBin", "Scan Bin"));


                break;
            }


            case DROP_OFF_BIN: {
                if (binId == null) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.InvalidBin"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);
                    return;
                }

                this.isBusy.show();
                dropoff dropoffBag = new dropoff();
                dropoff.request request = dropoffBag.newRequest();
                request.setBagId(this.bagId);
                request.setBinId(binId);

                Gson parser = new Gson();
                String result = parser.toJson(request);
                Crashlytics.log(1, "BagScanFragment.Pickup invoked", result);

                dropoffBag.execute(request, new INetFunctionDelegate<dropoff.request, dropoff.response>() {
                    @Override
                    public void netFunctionDidFail(NetFunction<dropoff.request, dropoff.response> function, Error error) {
                        Crashlytics.log(0, "BagScanFragment.DropOff failed", error.getContext());
                        dropoffDidFail(error.getContext());
                    }

                    @Override
                    public void netFunctionDidSucceed(NetFunction<dropoff.request, dropoff.response> function, FuncResponse<dropoff.response> result) {
                        if (result.isSuccess()) {
                            dropoffDidSucceed(result.getData());
                        } else {
                            String errorMessage = result.getMessages().get(0);
                            Crashlytics.log(0, "BagScanFragment.DropOff failed", errorMessage);
                            dropoffDidFail(errorMessage);
                        }
                    }
                });
                break;
            }


            case PICK_UP: {
                if (bagId == null) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.InvalidBag"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);

                    return;
                } else if (!bagId.contentEquals(this.bagId)) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.BagMismatch"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);

                    return;
                }
                this.isBusy.show();
                pickup pickupBag = new pickup();
                pickup.request request = pickupBag.newRequest();

                request.setBagId(this.bagId);
                request.setLocationId(pickupLocation);

                Gson parser = new Gson();
                String result = parser.toJson(request);
                Crashlytics.log(1, "BagScanFragment.Pickup invoked", result);

                pickupBag.execute(request, new INetFunctionDelegate<pickup.request, pickup.response>() {
                    @Override
                    public void netFunctionDidFail(NetFunction<pickup.request, pickup.response> function, Error error) {
                        Crashlytics.log(1, "BagScanFragment.Pickup failed", error.getContext());
                        pickupDidFail(error.getMessage());
                    }

                    @Override
                    public void netFunctionDidSucceed(NetFunction<pickup.request, pickup.response> function, FuncResponse<pickup.response> result) {
                        if (result.isSuccess()) {
                            pickupDidSucceed(result.getData().getWash().getId());
                        } else {
                            String errorMessage = result.getMessages().get(0);
                            Crashlytics.log(0, "BagScanFragment.Pickup failed", errorMessage);

                            pickupDidFail(errorMessage);
                        }
                    }
                });
            }

                break;
            case ACTIVATE: {

                if (bagId == null) {
                    ErrorPopup error = new ErrorPopup(getActivity(), "Error", TranslationService.getCurrent().get("Scanner.Error.InvalidBag"), new ErrorPopup.ErrorPopupDelegate() {
                        @Override
                        public void alertClosed() {
                        }
                    });
                    error.show();
                    mButton.setVisibility(View.VISIBLE);
                    return;
                }
                this.isBusy.show();
                activate activateBag = new activate();
                activate.request request = activateBag.newRequest();
                request.setBagId(bagId);

                Gson parser = new Gson();
                String result = parser.toJson(request);
                Crashlytics.log(1, "BagScanFragment.Activate invoked", result);

                activateBag.execute(request, new INetFunctionDelegate<activate.request, activate.response>() {
                    @Override
                    public void netFunctionDidFail(NetFunction<activate.request, activate.response> function, Error error) {
                        Crashlytics.log(0, "BagScanFragment.Activate failed", error.getContext());
                        activateDidFail(error.getMessage());

                    }

                    @Override
                    public void netFunctionDidSucceed(NetFunction<activate.request, activate.response> function, FuncResponse<activate.response> result) {

                        if (result.isSuccess()) {
                            activateDidSucceed(result.getData().getWash().getId());
                        } else {
                            String errorMessage = result.getMessages().get(0);
                            Crashlytics.log(0, "BagScanFragment.Activate failed", errorMessage);
                            activateDidFail(errorMessage);

                        }
                    }
                });
            }
        }


        if (bagId != null) {
            Toast.makeText(getActivity(), "Scanned: " + bagId, Toast.LENGTH_SHORT).show();
        } else if (binId != null) {
            Toast.makeText(getActivity(), "Scanned: " + binId, Toast.LENGTH_SHORT).show();
        }
    }

    //Scan Bag Server Result Methods

    private void activateDidSucceed(Long washId) {
        hasError = false;
        //_buttonContainer.setVisibility(View.VISIBLE);

        setButtonParameters(TranslationService.getCurrent().get("Scanner.Activate.Success.MainText"),
                TranslationService.getCurrent().get("Scanner.Activate.Success.SubText"),
                R.drawable.star,
                true,
                "#7AC93B");
        this.washId = washId;
        successUserUpdate();
        _buttonContainer.setVisibility(View.VISIBLE);
    }

    private void activateDidFail(String error) {
        System.out.println(TranslationService.getCurrent().get("Scanner.Error.Subtitle"));
        //Failed
        mButton.setVisibility(View.VISIBLE);
        _buttonContainer.setVisibility(View.VISIBLE);
        //Error;
        setButtonParameters(error,
                TranslationService.getCurrent().get("Scanner.Error.Subtitle"),
                R.drawable.exclamation,
                false,
                "#931E1A");
        hasError = true;
        isBusy.dismiss();
    }

    private void dropoffDidSucceed(dropoff.response response) {
        hasError = false;
        //_buttonContainer.setVisibility(View.VISIBLE);

        setButtonParameters(TranslationService.getCurrent().get("Scanner.DropOff.Success.MainText"),
                TranslationService.getCurrent().get("Scanner.DropOff.Success.SubText"),
                R.drawable.star,
                true,
                "#7AC93B");


        String specialInstructions = response.getLocation().getSpecialInstructions();
        specialInstructions = specialInstructions.replaceAll("(\\\\r\\\\n|\\\\n)", "<br />");
        String message = t.get("Success.Title");
        message += "<br/><br/>";
        message += "<br/><br/>";
        message += "<span style='color:yellow;'>" +specialInstructions + "</span>";

        new AlertDialog.Builder(this.getActivity())
                .setTitle(t.get("Success.Heading"))
                .setMessage(Html.fromHtml(message))
                .setPositiveButton(t.get("Success.Button.Done"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        successUserUpdate();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        //washId = result.getData().getWash().getId(); //Fails here
        _buttonContainer.setVisibility(View.VISIBLE);

    }

    private void dropoffDidFail(String error) {
        mButton.setVisibility(View.VISIBLE);
        _buttonContainer.setVisibility(View.VISIBLE);

        //Error;
        setButtonParameters(error,
                TranslationService.getCurrent().get("Scanner.Error.Subtitle"),
                R.drawable.exclamation,
                false,
                "#931E1A");
        hasError = true;
        isBusy.dismiss();

    }

    private void pickupDidSucceed(Long washId) {
        hasError = false;
        // _buttonContainer.setVisibility(View.VISIBLE);

        setButtonParameters(TranslationService.getCurrent().get("Scanner.Pickup.Success.MainText"),
                TranslationService.getCurrent().get("Scanner.Pickup.Success.SubText"),
                R.drawable.star,
                true,
                "#7AC93B");
        successUserUpdate();
        this.washId = washId;
        _buttonContainer.setVisibility(View.VISIBLE);
    }

    private void pickupDidFail(String error) {
        mButton.setVisibility(View.VISIBLE);
        _buttonContainer.setVisibility(View.VISIBLE);

        //Error;
        setButtonParameters(error,
                TranslationService.getCurrent().get("Scanner.Error.Subtitle"),
                R.drawable.exclamation,
                false,
                "#931E1A");
        hasError = true;
        isBusy.dismiss();
    }

}
