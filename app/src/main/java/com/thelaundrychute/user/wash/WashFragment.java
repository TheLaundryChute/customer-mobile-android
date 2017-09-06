package com.thelaundrychute.user.wash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.thelaundrychute.business.location.functions.getExpectedPickupDate;
import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.user.bag.BagScanActivity;
import com.thelaundrychute.user.bag.BagScanType;
import com.thelaundrychute.user.common.PinAlertDialog;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.common.fragments.DatePickerFragment;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.Toolbar;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.user.UserHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class WashFragment extends Fragment {

    private static final String ARG_WASH_ID = "wash_id";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;

    private Wash mWash;
    private TextView mTitleField;
    private ImageButton mNextWashButton;
    private ImageButton mPreviousWashButton;

    private TextView mActionWashButton;
    private TextView mExpectedDateTextView;

    private WashHelper mWashHelper;
    private int mBagSrc;
    private int mStatusBarSrc;
    private int mMainActionImageLeft;
    private int mMainActionImageRight;
    private int mLeftActionImage;
    private int mRightActionImage;


    private Spanned mLeftActionContent;
    private Spanned mRightActionContent;
    private Spanned mStatusBarContent;
    private Spanned mMainActionContent;

    private ViewPager mViewPager;

    public static WashFragment newInstance(long washId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WASH_ID, washId);

        WashFragment fragment = new WashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static HashMap<Integer, Drawable> StoredImages = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long washId = (long) getArguments().getSerializable(ARG_WASH_ID);
        mWash = WashRepository.get(getActivity()).getWash(washId);
        if (mWash == null) {
            mWash = UserHelper.getNonWash();
        }
        mWashHelper = new WashHelper(mWash);



        mBagSrc = mWashHelper.getBagSrc();
        mStatusBarSrc = mWashHelper.getStatusBarSrc();
        mMainActionContent = mWashHelper.getMainActionContent();
        mMainActionImageLeft = mWashHelper.getMainActionImageLeft();
        mMainActionImageRight = mWashHelper.getHelpActionImageRight();
        mLeftActionContent = mWashHelper.getLeftActionContent();
        mLeftActionImage = mWashHelper.getLeftActionImage();
        mRightActionContent = mWashHelper.getRightActionContent();
        mRightActionImage = mWashHelper.getRightActionImage();
        mStatusBarContent = mWashHelper.getStatusBarContent();



        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_wash_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return Toolbar.onOptionsItemSelected(getActivity(), item);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    private void setStoredImage(View v, int viewId, int resourceId, boolean hideIfInvalid) {
        ImageView imageView = (ImageView) v.findViewById(viewId);
        if (resourceId != -1) {
            Drawable d;
            if (!StoredImages.containsKey(resourceId)){

                /////////////////////////////////////

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), resourceId, options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                String imageType = options.outMimeType;

                options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight);
                options.inJustDecodeBounds = false;
                BitmapFactory.decodeResource(getResources(), resourceId, options);

                d = new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources(), resourceId, imageWidth, imageHeight));


                ////////////////////////////////////




                //d = PictureUtils.getScaledBitmap(getActivity(), resourceId,
                //        imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight());
                //d = PictureUtils.getScaledBitmap(getActivity(), resourceId, imageView);
                //d = new BitmapDrawable(getResources(), decodeFile(getResources().getDrawable(resourceId, getActivity().getTheme())));

                StoredImages.put(resourceId, d);
            }
            d = StoredImages.get(resourceId);

            imageView.setImageDrawable(d);
        } else if(hideIfInvalid) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wash, container, false);
        Toolbar.init(getActivity(), v);

        //This is done this way for scrolling performance
        setStoredImage(v, R.id.bag_image_view, mBagSrc, false);

        setStoredImage(v, R.id.wash_status, mStatusBarSrc, true);

        ImageView actionHelpLeftImage = (ImageView) v.findViewById(R.id.action_help_left_image);
        actionHelpLeftImage.setImageResource(mMainActionImageLeft);

        TextView leftActionText = (TextView) v.findViewById(R.id.actionOneLabel);
        leftActionText.setText(mLeftActionContent);

        ImageView leftActionImageView = (ImageView) v.findViewById(R.id.leftSubButtonImage);
        leftActionImageView.setImageResource(mLeftActionImage);

        if (R.id.action_help_right_image != -1) {
            ImageView actionHelpRightImage = (ImageView) v.findViewById(R.id.action_help_right_image);
            actionHelpRightImage.setImageResource(mMainActionImageRight);
        }

        TextView rightActionText = (TextView) v.findViewById(R.id.actionTwoLabel);
        rightActionText.setText(mRightActionContent);

        ImageView rightActionImageView = (ImageView) v.findViewById(R.id.rightSubButtonImage);
        rightActionImageView.setImageResource(mRightActionImage);

        TextView  actionText = (TextView) v.findViewById(R.id.action_notification);
        actionText.setText(mStatusBarContent);

        //Activate the banner button if necessary
        if (UserHelper.getActionitem().getTarget() != null && UserHelper.getActionitem().getTarget().contentEquals("WEB")) {
            final String webParameter = UserHelper.getActionitem().getParameters().get("path");
            if (webParameter != null) {
                actionText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (webParameter.contentEquals("/plan/select")) {
                            PinAlertDialog alert = new PinAlertDialog(getActivity(), UserHelper.getUser().getPin(), new PinAlertDialog.PinAlertDialogDelegate() {
                                @Override
                                public void pinEntryValidated() {
                                    Intent intent = WebActivity.newIntent(getActivity(), webParameter.substring(1));
                                    startActivity(intent);
                                }

                                @Override
                                public void pinEntryCanceled() {
                                }
                            });
                        } else {
                            Intent intent = WebActivity.newIntent(getActivity(), webParameter.substring(1));
                            startActivity(intent);
                        }
                    }
                });
            }
        }

        mTitleField = (TextView) v.findViewById(R.id.wash_title);
        if (mWash.getBag().getId() != null && mWash.getBag().getId() != "") {
            mTitleField.setText(mWash.getBag().getId());
        } else {
            mTitleField.setVisibility(View.INVISIBLE);
        }


        mViewPager = (ViewPager) getActivity().findViewById(R.id.activity_wash_pager_view_pager);

        mNextWashButton = (ImageButton)v.findViewById(R.id.wash_next);
        mNextWashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });

        mPreviousWashButton = (ImageButton) v.findViewById(R.id.wash_previous);
        mPreviousWashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 0) {
                    mPreviousWashButton.setVisibility(View.INVISIBLE);
                } else {
                    mPreviousWashButton.setVisibility(View.VISIBLE);
                }

                if ((position + 1) == mViewPager.getAdapter().getCount()) {
                    mNextWashButton.setVisibility(View.INVISIBLE);
                } else {
                    mNextWashButton.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //Help Action, dependent on bag status
        mActionWashButton = (TextView)v.findViewById(R.id.wash_action);
        mActionWashButton.setText(mMainActionContent);

        LinearLayout actionHelp = (LinearLayout)v.findViewById(R.id.action_help);
        actionHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent;

                switch(mWash.getBag().getStatus().getName()) {
                    case "ACTIVE":
                        //Find dropoff
                        intent = WebActivity.newIntent(getActivity(), WebPages.LOCATIONS_DROPOFF, "/" + mWash.getId());
                        startActivity(intent);

                        break;
                    case "DROPPED_OFF":
                    case "PICKED_UP":
                    case "IN_TRANSIT":
                    case "PROCESSING":
                    case "ENROUTE":
                        //Nothing
                        break;

                    case "WAITING":
                        //See pickup location
                        intent = WebActivity.newIntent(getActivity(), WebPages.LOCATIONS_PICKUP, "/" + mWash.getId());
                        startActivity(intent);

                        break;
                    default:
                        //Find Bag Locations
                        intent = WebActivity.newIntent(getActivity(), WebPages.LOCATIONS_BAG_PICKUP);
                        startActivity(intent);
                        break;
                }
            }
        });

        //Action One, dependent on bag status
        LinearLayout actionOneLayout = (LinearLayout)v.findViewById(R.id.actionOne);
        actionOneLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent;
                switch(mWash.getBag().getStatus().getName()) {
                    case "ACTIVE":
                        //Customize Wash
                        intent = WebActivity.newIntent(getActivity(), WebPages.CUSTOMIZE, "/" + mWash.getId());
                        startActivity(intent);

                        break;
                    case "DROPPED_OFF":
                    case "PICKED_UP":
                    case "IN_TRANSIT":
                    case "PROCESSING":
                    case "ENROUTE":
                        //Pickup Location
                        intent = WebActivity.newIntent(getActivity(), WebPages.LOCATIONS_PICKUP, "/" + mWash.getId());
                        startActivity(intent);

                        break;
                    case "WAITING":
                        //Pickup Scan
                        intent = BagScanActivity.newIntent(getActivity(), BagScanType.PICK_UP, mWash.getBag().getId(), mWash.getPickupLocation().getId());
                        startActivity(intent);

                        break;
                    default:
                        //Preferences
                        intent = WebActivity.newIntent(getActivity(), WebPages.PREFERENCES);

                        startActivity(intent);
                        break;
                }
            }
        });

        //Action Two, dependent on bag status
        LinearLayout actionTwoLayout = (LinearLayout)v.findViewById(R.id.actionTwo);
        actionTwoLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent;
                switch(mWash.getBag().getStatus().getName()) {
                    case "ACTIVE":
                        //DropOff Scan

                        new PinAlertDialog(getActivity(), UserHelper.getUser().getPin(), new PinAlertDialog.PinAlertDialogDelegate() {
                            @Override
                            public void pinEntryValidated() {
                                Intent intent = WebActivity.newIntent(getActivity(), WebPages.DROP_OFF, "/" + mWash.getId());
                                startActivity(intent);
                            }

                            @Override
                            public void pinEntryCanceled() {
                            }
                        });

                        break;
                    case "DROPPED_OFF":
                    case "PICKED_UP":
                    case "IN_TRANSIT":
                    case "PROCESSING":
                    case "ENROUTE":
                    case "WAITING":
                        //Wash Feedback
                        intent = WebActivity.newIntent(getActivity(), WebPages.FEEDBACK, "/" + mWash.getId());
                        startActivity(intent);
                        break;
                    default:
                        //Scan (activate)
                        if (!UserHelper.hasAvailableWashes()) {
                            TranslationService t = TranslationService.getCurrent();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle(t.get("Error"));
                            alert.setMessage(t.get("BagList.Alert.NoWash", "You must buy a new plan to continue"));
                            alert.setPositiveButton(t.get("Common.Alert.Title.BuyPlan", "Buy Plan"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = WebActivity.newIntent(getActivity(), WebPages.BUY_PLAN);
                                    startActivity(intent);
                                }
                            });
                            alert.setNegativeButton(t.get("Common.Cancel", "Cancel"), null);
                            alert.show();
                        } else {
                            intent = BagScanActivity.newIntent(getActivity(), BagScanType.ACTIVATE, mWash.getBag().getId(), null);
                            startActivity(intent);
                        }


                        break;
                }
            }
        });

        this.mExpectedDateTextView = (TextView)v.findViewById(R.id.expectedDateTextView);
        this.setExpectedPickupDate();

        //Handle picked up bag
        if (mWash.getBag().getStatus().getName().contentEquals("PICKED_UP")) {
            actionHelp.setVisibility(View.INVISIBLE);
            actionOneLayout.setVisibility(View.INVISIBLE);
            actionTwoLayout.setVisibility(View.INVISIBLE);

            Intent intent = WashReview.newIntent(getActivity(), mWash.getBag().getId(), mWash.getId(), mWash.getPickupLocation().getId());
            startActivity(intent);
        }

        return v;
    }

    private void setExpectedPickupDate() {
        getExpectedPickupDate getExpectedPickupDate = new getExpectedPickupDate();
        getExpectedPickupDate.request request = getExpectedPickupDate.newRequest();
        request.setWashId(mWash.getId());
        getExpectedPickupDate.execute(request, new INetFunctionDelegate<getExpectedPickupDate.request, getExpectedPickupDate.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<getExpectedPickupDate.request, getExpectedPickupDate.response> function, com.inMotion.core.Error error) {

                mExpectedDateTextView.setVisibility(View.GONE);
                mExpectedDateTextView.setText("");
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<getExpectedPickupDate.request, getExpectedPickupDate.response> function, FuncResponse<getExpectedPickupDate.response> result) {

                //Log.d("RYAN", "check out the value of result: " + result.getData().getExpectedPickupDate().toString());
                Date expectedDate = result.getData().getExpectedPickupDate();
                String dateString = new SimpleDateFormat("MM/dd/yy").format(expectedDate);
                String contents = "<b><font color=\"#FFD479\">" + TranslationService.getCurrent().get("BagList.ExpectedPickup") + "</b></font><font color =\"#FFFFFF\">" + " " + dateString + "</font><br />";

                mExpectedDateTextView.setText(Html.fromHtml(contents));
                mExpectedDateTextView.setVisibility(View.VISIBLE);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //mWash.setDate(date);

        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return
            // values for.
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME,
            };
            // Perform your query - the contactUri is like a "where"
            // clause here
            ContentResolver resolver = getActivity().getContentResolver();
            Cursor c = resolver
                    .query(contactUri, queryFields, null, null, null);

            try {
                // Double-check that you actually got results
                if (c.getCount() == 0) {
                    return;
                }

                // Pull out the first column of the first row of data -
                // that is your suspect's name.
                c.moveToFirst();

            } finally {
                c.close();
            }
        }
    }





}
