package com.thelaundrychute.user.wash;

import android.text.Html;
import android.text.Spanned;

import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.user.UserHelper;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class WashHelper {
    private Wash mWash;
    private int mBagSrc;
    private int mStatusBarSrc;


    private Spanned mMainActionSpanned;
    private int mMainActionImageLeft;
    private int mMainActionImageRight;

    private Spanned mLeftActionSpanned;
    private int mLeftActionImage;

    private Spanned mRightActionSpanned;
    private int mRightActionImage;


    private Spanned mStatusBarSpanned;


    private getCurrent.actionItem item;

    public WashHelper(Wash wash) {

        TranslationService t = TranslationService.getCurrent();
        mWash = wash;
        mBagSrc = -1;
        mStatusBarSrc = -1;
        mStatusBarSpanned = UserHelper.getUserMessage() != null ? Html.fromHtml(UserHelper.getUserMessage()) : null;
        item = UserHelper.getActionitem();



        if (wash != null && wash.getId() == -1) {
            mBagSrc = R.drawable.nobag;
             mMainActionImageLeft = R.drawable.help;
            mMainActionImageRight = R.drawable.arrow;
            mMainActionSpanned =
                    getHelpActionSpanned(t.get("BagList.NoBag.MainText", "You don't have any bags!", null), t.get("BagList.NoBag.SubText", "Find locations where you can get one", null));

            mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.NoBag.LeftButton", "Set Laundry Preferences", null), null);
            mLeftActionImage = R.drawable.check;

            mRightActionSpanned = getHelpActionSpanned(t.get("BagList.NoBag.RightButton", "Scan Bags", null),null);
            mRightActionImage = R.drawable.qrcode;


            return;
        }

        switch(mWash.getBag().getStatus().getName()) {
            case "INACTIVE":
                //We don't show inactive washes/bags
                break;
            case "ACTIVE":
                mBagSrc = R.drawable.bag;
                mStatusBarSrc = R.drawable.step1;
                 mMainActionImageLeft = R.drawable.help;
                mMainActionImageRight = R.drawable.arrow;
                mMainActionSpanned =
                        getHelpActionSpanned(t.get("BagList.Steps.1.MainText", "Ready to drop this bag off?"), t.get("BagList.Steps.1.SubText", "Find a drop-off location."));
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.1.LeftButton"), null);
                mLeftActionImage = R.drawable.gear;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.1.RightButton"), null);
                mRightActionImage = R.drawable.qrcode;

                break;
            case "DROPPED_OFF":
                mBagSrc = R.drawable.bag;
                mStatusBarSrc = R.drawable.step2;
                 mMainActionImageLeft = R.drawable.clock;
                mMainActionImageRight = -1;
                mMainActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.2.MainText"), null);
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.2.LeftButton"), null);
                mLeftActionImage = R.drawable.mapwhite;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.2.RightButton"), null);
                mRightActionImage = R.drawable.conversation;

                break;
            case "IN_TRANSIT":
                mBagSrc = R.drawable.bagleaving;
                mStatusBarSrc = R.drawable.step3;
                 mMainActionImageLeft = R.drawable.truck;
                mMainActionImageRight = -1;
                mMainActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.3.MainText", null, new String[]{mWash.getBag().getId()}),
                        t.get("BagList.Steps.3.SubText", null, null));
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.3.LeftButton"), null);
                mLeftActionImage = R.drawable.mapwhite;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.3.RightButton"), null);
                mRightActionImage = R.drawable.conversation;


                break;
            case "PROCESSING":
                mBagSrc = R.drawable.bagwashing;
                mStatusBarSrc = R.drawable.step4;
                 mMainActionImageLeft = R.drawable.clock;
                mMainActionImageRight = -1;

                mMainActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.4.MainText", null, new String[]{mWash.getBag().getId()}),
                        t.get("BagList.Steps.4.SubText", "We’ll let you know when it’s done.", null));
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.4.LeftButton"), null);
                mLeftActionImage = R.drawable.mapwhite;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.4.RightButton"), null);
                mRightActionImage = R.drawable.conversation;
                break;
            case "SCHEDULED":
                mBagSrc = R.drawable.bag;
                break;
            case "ENROUTE":
                mBagSrc = R.drawable.bagreturning;
                mStatusBarSrc = R.drawable.step5;
                 mMainActionImageLeft = R.drawable.truck;
                mMainActionImageRight = -1;

                mMainActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.5.MainText", null, new String[]{mWash.getBag().getId()}),
                        t.get("BagList.Steps.5.SubText", "Your clean laundry will be ready for pick-up soon.", null));
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.5.LeftButton"), null);
                mLeftActionImage = R.drawable.mapwhite;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.5.RightButton"), null);
                mRightActionImage = R.drawable.conversation;
                break;
            case "WAITING":
                mBagSrc = R.drawable.bagwaiting;
                mStatusBarSrc = R.drawable.step6;
                mMainActionImageLeft = R.drawable.star;
                mMainActionImageRight = R.drawable.arrow;

                mMainActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.6.MainText"), t.get("BagList.Steps.6.SubText", ""));
                mLeftActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.6.LeftButton"), null);
                mLeftActionImage = R.drawable.qrcode;
                mRightActionSpanned = getHelpActionSpanned(t.get("BagList.Steps.6.RightButton"), null);
                mRightActionImage = R.drawable.conversation;
                break;
            case "PICKED_UP":
                mBagSrc = R.drawable.bagclean;
                mStatusBarSrc = R.drawable.step7;
                break;

        }

    }

    private Spanned getHelpActionSpanned(String label, String subLabel) {
        String contents = "<b><big>" + label + "</big></b>"+ "<br />";
        if (subLabel != null && subLabel != "") {
            contents +="<small>" + subLabel + "</small>" + "<br />";
        }

        return Html.fromHtml(contents);
    }

    public Spanned getMainActionContent() {
        return mMainActionSpanned;
    }

    public Spanned getStatusBarContent() {
        return mStatusBarSpanned;
    }

    public int getBagSrc() {

        return mBagSrc;
    }

    public int getMainActionImageLeft() {
        return  mMainActionImageLeft;
    }

    public int getHelpActionImageRight() {
        return mMainActionImageRight;
    }

    public Spanned getLeftActionContent() { return mLeftActionSpanned; }

    public int getLeftActionImage() { return mLeftActionImage; }

    public Spanned getRightActionContent() { return mRightActionSpanned; }

    public int getRightActionImage() { return mRightActionImage; }



    public void setHelpActionImageLeft(int mHelpActionImageLeft) {
        this. mMainActionImageLeft = mHelpActionImageLeft;
    }

    public int getStatusBarSrc() {


        return mStatusBarSrc;
    }
}
