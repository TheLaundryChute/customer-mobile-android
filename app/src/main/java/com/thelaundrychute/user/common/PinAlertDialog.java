package com.thelaundrychute.user.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.user.UserHelper;


/**
 * Created by ryancoyle on 12/22/15.
 */
public class PinAlertDialog extends AlertDialog.Builder {

    public interface PinAlertDialogDelegate {
        void pinEntryValidated();
        void pinEntryCanceled();
    }

    public Context mContext;
    private View dialogLayout;
    private EditText input;
    private AlertDialog reference;
    private PinAlertDialogDelegate mDelegate;


    public PinAlertDialog(Context context, final String userPin, final PinAlertDialogDelegate callback) {
        super(context);
        this.mContext = context;
        this.mDelegate = callback;

        TranslationService t = TranslationService.getCurrent();
        this.setTitle(t.get("Common.Pin.Header"));
        this.setMessage("");

        this.dialogLayout = View.inflate(this.mContext, R.layout.dialog_login, null);
        this.setView(this.dialogLayout);

        input = (EditText)this.dialogLayout.findViewById(R.id.txtPIN);
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    donePressed();
                    return true;
                }
                return false;
            }
        });

        this.setPositiveButton(t.get("Common.Ok", "Ok"), null);
        this.setNegativeButton(t.get("Common.Cancel", "Cancel"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.pinEntryCanceled();
            }
        });


        input.requestFocus();
        (new Handler()).postDelayed(new Runnable() {
            public void run() {

                input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
            }
        }, 200);
        reference = this.show();
        reference.setCancelable(false);
        reference.setCanceledOnTouchOutside(false);
        reference.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                donePressed();
            }
        });
    }

    public void donePressed() {
        if (UserHelper.getUser().getPin().contentEquals(input.getText().toString())) {
            mDelegate.pinEntryValidated();
            reference.dismiss();
        } else {
            TranslationService t = TranslationService.getCurrent();
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle(t.get("Common.Pin.Invalid.Title", "Error: Incorrect Pin"));
            alert.setMessage(t.get("Common.Pin.Invalid.Message", "Please re-enter your Pin"));
            alert.setPositiveButton(t.get("Common.Ok"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    input.requestFocus();
                    input.setText("");

                    (new Handler()).postDelayed(new Runnable() {
                        public void run() {

                            input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                            input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
                        }
                    }, 200);
                    //input.setSelection(input.getText().length());
                }
            });
            alert.show();
        }
    }



}
