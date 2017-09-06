package com.thelaundrychute.user.wash;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.bag.ReuseBagActivity;
import com.thelaundrychute.user.common.TranslationService;

import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class WashReviewFragment extends Fragment {

    private TextView mHeaderTextView;
    private TextView mInstructionsTextView;
    private TextView mPoorTextView;
    private TextView mGreatTextView;

    private Button mContinueButton;
    private Button mNoThanksButton;
    private Button mOneButton;
    private Button mTwoButton;
    private Button mThreeButton;
    private Button mFourButton;
    private Button mFiveButton;
    private List<Button> buttonList;

    private int rating = 1;
    private String bagId;
    private String washId;
    private String locationId;

    public WashReviewFragment() {
    }

    public static WashReviewFragment newInstance() {
        WashReviewFragment fragment = new WashReviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras == null) {
                this.bagId = null;
                this.locationId = null;
                this.washId = null;
            } else {
                this.bagId = extras.getString("bagId");
                this.washId =  Long.toString(extras.getLong("washId"));
                this.locationId = extras.getString("locationId");
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wash_review, container, false);

        this.mHeaderTextView = (TextView) view.findViewById(R.id.headerTextView);
        this.mInstructionsTextView = (TextView) view.findViewById(R.id.instructionsTextView);
        this.mPoorTextView = (TextView) view.findViewById(R.id.poorTextView);
        this.mGreatTextView = (TextView) view.findViewById(R.id.greatTextView);

        this.mOneButton = (Button) view.findViewById(R.id.oneButton);
        this.mOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(1);
            }
        });

        this.mTwoButton = (Button) view.findViewById(R.id.twoButton);
        this.mTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(2);
            }
        });

        this.mThreeButton = (Button) view.findViewById(R.id.threeButton);
        this.mThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(3);
            }
        });

        this.mFourButton = (Button) view.findViewById(R.id.fourButton);
        this.mFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(4);
            }
        });

        this.mFiveButton = (Button) view.findViewById(R.id.fiveButton);
        this.mFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(5);
            }
        });

        this.buttonList = Arrays.asList(this.mOneButton, this.mTwoButton, this.mThreeButton, this.mFourButton, this.mFiveButton);

        this.mContinueButton = (Button) view.findViewById(R.id.continueButton);
        this.mContinueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = WashComments.newIntent(getActivity());
                intent.putExtra("bagId", bagId);
                intent.putExtra("washId", washId);
                intent.putExtra("locationId", locationId);
                intent.putExtra("rating", rating);
                startActivity(intent);
            }
        });



        this.mNoThanksButton = (Button) view.findViewById(R.id.noThanksButton);
        this.mNoThanksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = ReuseBagActivity.newIntent(getActivity());
                intent.putExtra("bagId", bagId);
                intent.putExtra("locationId", locationId);
                startActivity(intent);
            }
        });

        this.setTranslations();





        return view;
    }

    private void setTranslations() {
        TranslationService t = TranslationService.getCurrent();
        this.mHeaderTextView.setText(t.get("Wash.Rating.Title"));
        this.mInstructionsTextView.setText(t.get("Wash.Rating.PageContext"));
        this.mPoorTextView.setText(t.get("Wash.Rating.Poor"));
        this.mGreatTextView.setText(t.get("Wash.Rating.Great"));

        this.mContinueButton.setText(t.get("Common.Continue"));
        this.mNoThanksButton.setText(t.get("Common.NoThanks"));
    }


    private void setRating(int rating) {
        this.rating = rating;
        if (mContinueButton.getVisibility() == View.INVISIBLE) {
            mContinueButton.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < rating; i++) {
            this.buttonList.get(i).getBackground().setColorFilter(Color.parseColor("#FFD479"), PorterDuff.Mode.MULTIPLY);
        }

        for (int j = rating; j < 5; j++) {
            this.buttonList.get(j).getBackground().clearColorFilter();
        }
    }
}
