package com.thelaundrychute.user.wash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.session.Context;
import com.thelaundrychute.business.message.FeedbackMessage;
import com.thelaundrychute.business.user.functions.sendMessageFromUser;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.bag.ReuseBagActivity;
import com.thelaundrychute.user.common.ErrorPopup;
import com.thelaundrychute.user.common.TranslationService;

/**
 * A placeholder fragment containing a simple view.
 */
public class WashCommentsFragment extends Fragment {

    private TextView mHeaderTextView;
    private TextView mInstructionsTextView;
    private TextView mCommentsHeaderTextView;

    private Button mContinueButton;
    private Button mNoThanksButton;

    private EditText mCommentsEditText;

    //TODO:
    //NEED BOTH OF THESE

    private String washId;
    private String bagId;
    private String locationId;
    private Integer rating;

    private ProgressDialog isBusy;


    public static WashCommentsFragment newInstance() {
        return new WashCommentsFragment();
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
                this.washId = null;
                this.rating = 0;
            } else {
                this.bagId = extras.getString("bagId");
                this.washId = extras.getString("washId");
                this.locationId = extras.getString("locationId");
                this.rating = extras.getInt("rating");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wash_comments, container, false);

        this.mHeaderTextView = (TextView) view.findViewById(R.id.headerTextView);
        this.mInstructionsTextView = (TextView) view.findViewById(R.id.instructionsTextView);
        this.mCommentsHeaderTextView = (TextView) view.findViewById(R.id.anyCommentsTextView);
        this.mCommentsEditText = (EditText) view.findViewById(R.id.commentsEditText);
        this.mCommentsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mContinueButton.setVisibility(View.VISIBLE);
                } else {
                    mContinueButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.mContinueButton = (Button) view.findViewById(R.id.continueButton);
        this.mContinueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitWashFeedback();

            }
        });


        this.mNoThanksButton = (Button) view.findViewById(R.id.noThanksButton);
        this.mNoThanksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitWashFeedback();
            }
        });

        this.setTranslations();

        return view;
    }

    private void submitWashFeedback() {

        sendMessageFromUser sendMessageFromUser = new sendMessageFromUser();
        sendMessageFromUser.request request = sendMessageFromUser.newRequest();

        FeedbackMessage message = new FeedbackMessage("", this.mCommentsEditText.getText().toString(), this.washId, "REVIEW_WASH", Context.getCurrent().getUser().getId(), this.rating);

        this.isBusy.show();
        request.setmMessage(message);
        request.setmAddFeedback(true);
        request.setmSendMessage(false);

        Gson parser = new Gson();
        String result = parser.toJson(request);
        Crashlytics.log(1, "WashCommentsFragment.SendMessage invoked", result);


        sendMessageFromUser.execute(request, new INetFunctionDelegate<sendMessageFromUser.request, sendMessageFromUser.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<sendMessageFromUser.request, sendMessageFromUser.response> function, Error error) {
                Crashlytics.log(0, "WashCommentsFragment.SendMessage failed", error.getContext());
                feedbackAddFailed(error.getMessage());
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<sendMessageFromUser.request, sendMessageFromUser.response> function, FuncResponse<sendMessageFromUser.response> result) {
                if (result.isSuccess()) {
                    feedbackAddSuccess();
                } else {
                    String errorMessage = result.getMessages().get(0);
                    Crashlytics.log(0, "WashCommentsFragment.SendMessage failed", errorMessage);
                    feedbackAddFailed(errorMessage);
                }
            }
        });
    }


    private void setTranslations() {
        TranslationService t = TranslationService.getCurrent();
        this.mHeaderTextView.setText(t.get("Wash.Comments.Title"));
        this.mInstructionsTextView.setText(t.get("Wash.Comments.PageContext"));
        this.mCommentsHeaderTextView.setText(t.get("Wash.Comments.TextView.Title"));
        this.mContinueButton.setText(t.get("Common.Continue"));
        this.mNoThanksButton.setText(t.get("Common.NoThanks"));
    }

    private void feedbackAddSuccess() {
        isBusy.dismiss();
        Intent intent = ReuseBagActivity.newIntent(getActivity());
        intent.putExtra("bagId", bagId);
        intent.putExtra("locationId", locationId);
        startActivity(intent);
    }

    private void feedbackAddFailed(String error) {
        isBusy.dismiss();
        ErrorPopup errorPopup = new ErrorPopup(getActivity(), "Error", error, new ErrorPopup.ErrorPopupDelegate() {
            @Override
            public void alertClosed() {
                Intent intent = ReuseBagActivity.newIntent(getActivity());
                intent.putExtra("bagId", bagId);
                intent.putExtra("locationId", locationId);
                startActivity(intent);
            }
        });
        errorPopup.show();
    }
}
