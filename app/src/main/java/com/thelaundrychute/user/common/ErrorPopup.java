package com.thelaundrychute.user.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by ryancoyle on 12/29/15.
 */
public class ErrorPopup extends AlertDialog.Builder {

    public interface ErrorPopupDelegate {
        void alertClosed();
    }

    private Context mContext;
    private View dialogLayout;


    public ErrorPopup(Context context, String title, String message, final ErrorPopupDelegate callback) {
        super(context);
        this.mContext = context;

        TranslationService t = TranslationService.getCurrent();
        this.setTitle(title);
        this.setMessage(message);

        //this.dialogLayout = View.inflate(this.mContext, R.layout.dialog_login, null);
       // this.setView(this.dialogLayout);

        this.setPositiveButton(t.get("Common.Ok"), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.alertClosed();
            }
        });

    }
}
