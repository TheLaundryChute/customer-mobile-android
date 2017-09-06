package com.thelaundrychute.user.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.inMotion.core.Error;
import com.inMotion.core.net.repositories.delegates.INetFunctionDelegate;
import com.inMotion.core.net.repositories.funcs.FuncResponse;
import com.inMotion.core.net.repositories.funcs.NetFunction;
import com.inMotion.core.net.repositories.funcs.emptyFuncRequest;
import com.inMotion.session.Context;
import com.thelaundrychute.business.user.functions.getCurrent;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.TLCLoginActivity;
import com.thelaundrychute.user.bag.BagScanActivity;
import com.thelaundrychute.user.bag.BagScanType;
import com.thelaundrychute.user.common.web.WebActivity;
import com.thelaundrychute.user.common.web.WebPages;
import com.thelaundrychute.user.user.UserHelper;
//import com.thelaundrychute.user.wash.WashPagerActivity;

/**
 * Created by Steve Baleno on 12/9/2015.
 */
public class Toolbar {
    static private ProgressDialog isBusy;


    public static void init(Activity context) {
        android.support.v7.widget.Toolbar toolbarBottom = (android.support.v7.widget.Toolbar) context.findViewById(R.id.toolbar_bottom);
        init(context, toolbarBottom);
    }
    public static void init(Activity context, View view) {
        android.support.v7.widget.Toolbar toolbarBottom = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar_bottom);
        init(context, toolbarBottom);
    }

    private static void init(final Activity context, android.support.v7.widget.Toolbar toolbarBottom) {
        if (Context.getCurrent().getAuthorization() == null) {
            toolbarBottom.setVisibility(View.INVISIBLE);
            toolbarBottom.getLayoutParams().height = 0;

            toolbarBottom.setLayoutParams(toolbarBottom.getLayoutParams());
            return;
        }

        toolbarBottom.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                switch(item.getItemId()){
                    case R.id.home:
                        isBusy = new ProgressDialog(context);
                        isBusy.setTitle(TranslationService.getCurrent().get("Android.Loading.Title", "Processing"));
                        isBusy.setMessage(TranslationService.getCurrent().get("Android.Loading.SubText", "Please wait"));

                        isBusy.show();
                        updateUserSettings(context);

                        return true;
                        //break;
                    case R.id.messages:
                        intent = WebActivity.newIntent(context, WebPages.MESSAGES);
                        break;
                    case R.id.wash_history:
                        intent = WebActivity.newIntent(context, WebPages.HISTORY);
                        break;
                    case R.id.user_profile:
                        intent = WebActivity.newIntent(context, WebPages.PROFILE);
                        break;
                    case R.id.faq:
                        intent = WebActivity.newIntent(context, WebPages.FAQ);
                        break;
                    case R.id.logout:

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("");
                        alert.setMessage(TranslationService.getCurrent().get("BagList.Logout.Message", "Are you sure you want to logout?", new String[]{Context.getCurrent().getUser().getName().getFirst()}));
                        alert.setPositiveButton(TranslationService.getCurrent().get("BagList.Logout.Confirm", "Yes, Logout"), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                com.inMotion.session.Context.getCurrent().logout();
                                Intent intent = new Intent(context, TLCLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                context.finish();
                            }
                        });
                        alert.setNegativeButton(TranslationService.getCurrent().get("Common.Alert.No", "No"), null);
                        alert.show();
                        //alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(neededColor);

                        break;
                    case R.id.action_settings:
                        // TODO
                        break;
                    // TODO: Other cases
                }

                if (intent != null) {
                    context.startActivity(intent);
                }
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbarBottom.inflateMenu(R.menu.fragment_app);
    }

    public static boolean onOptionsItemSelected(final Activity context, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_bag:

                if (!UserHelper.hasAvailableWashes()) {
                    TranslationService t = TranslationService.getCurrent();
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Error");
                    alert.setMessage(t.get("BagList.Alert.NoWash", "You must buy a new plan to continue"));
                    alert.setPositiveButton(t.get("Common.Alert.Title.BuyPlan", "Buy Plan"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = WebActivity.newIntent(context, WebPages.BUY_PLAN);
                            context.startActivity(intent);
                        }
                    });
                    alert.setNegativeButton(t.get("Common.Cancel", "Cancel"), null);
                    alert.show();

                } else {
                    Intent intent = BagScanActivity.newIntent(context, BagScanType.ACTIVATE, null, null);
                    context.startActivity(intent);
                }



                return true;
            /*case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;*/
            default:
                return context.onOptionsItemSelected(item);
        }
    }

    private static void updateUserSettings(final Activity context) {

        getCurrent request = new getCurrent();
        request.execute(null, new INetFunctionDelegate<emptyFuncRequest, getCurrent.response>() {
            @Override
            public void netFunctionDidFail(NetFunction<emptyFuncRequest, getCurrent.response> function, Error error) {
                updateUserDidFail(context, error.getMessage());
            }

            @Override
            public void netFunctionDidSucceed(NetFunction<emptyFuncRequest, getCurrent.response> function, FuncResponse<getCurrent.response> result) {
                if (result.isSuccess()) {
                    getCurrent.response data = result.getData();
                    UserHelper.setUser(data.getUser());
                    UserHelper.setUserMessage(data.getDetails().getActionItem().getMessage());
                    UserHelper.setActionItem(data.getDetails().getActionItem());

                    //Intent intent = WashPagerActivity.newIntent(context, false);
                    Intent intent = WebActivity.newIntent(context, WebPages.HOME);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                    isBusy.dismiss();
                } else {
                    String errorMessage = result.getMessages().get(0);
                    updateUserDidFail(context, errorMessage);
                }

            }
        });
    }

    private static void updateUserDidFail(final Activity context, String error) {
        //Error
        ErrorPopup errorPopup = new ErrorPopup(context, "Error", error, new ErrorPopup.ErrorPopupDelegate() {
            @Override
            public void alertClosed() {
                Context.getCurrent().logout();
                Intent intent = new Intent(context, TLCLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                context.finish();
            }
        });
        errorPopup.show();
    }
}
