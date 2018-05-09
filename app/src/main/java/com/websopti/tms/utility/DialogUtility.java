package com.websopti.tms.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Ankur on 01-12-2015.
 */
public class DialogUtility {

    private static final String TAG = DialogUtility.class.getSimpleName();

    private static final String SUCCESS_MESSAGE = "Records submitted successfully.";

    private static final String ERROR_MESSAGE = "Oops !! Please try again later";

    public static final String PREFERENCE_NAME = "MitMoldPref";

    private static SharedPreferences sharedPreferences;


    public static ProgressDialog processDialog(Context context, String message, boolean isCancelable) {
        try {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(isCancelable);
            dialog.show();
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
