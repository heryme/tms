package com.websopti.tms.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.websopti.tms.R;
import com.websopti.tms.constant.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rahul Padaliya on 9/12/2016.
 */
public class ProjectUtils {

    /***
     * Edit Text Validation
     * @param context
     * @param editText
     * @param msg
     * @return
     */
    public static boolean checkEditTextValidation(Context context, EditText editText, String msg) {

        if (editText != null) {
            if (editText.getText().toString().trim().length() == 0 && editText.isShown()) {

               // Animation shake = AnimationUtils.loadAnimation(context, R.anim.error_shake);
               // editText.startAnimation(shake);
                if(msg != null)
                    //editText.setError(msg);
                    //Set Error Dialog
                    ProjectUtils.showEmailErrorDialog(context, com.websopti.tms.login.constant.Constant.ERROR_MSG);
                else
                    editText.setError(Constant.ERROR_MSG_REQUIRED_FIELD);

                editText.requestFocus();
                editText.setFocusable(true);
              //ProjectUtility.showKeyboard(context, editText);
                return false;
            }

        } else {

            return false;
        }
        return true;
    }

    /**
     * Email validation
     * @param context
     * @param editText
     * @param msg
     * @return
     */
    public final static boolean isValidEmail(Context context, EditText editText, String msg) {

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches()){
          //  Animation shake = AnimationUtils.loadAnimation(context, R.anim.error_shake);
            // editText.startAnimation(shake);
            if(msg != null)
                editText.setError(msg);

            editText.requestFocus();
            editText.setFocusable(true);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Set ListView Height According To the Data
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * Email Validation
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    /**
     * Get query string
     * @param params
     * @return
     */
    public static String getQueryString(String... params) {

        StringBuffer strBuilder = new StringBuffer();

        for (int i = 0; i< params.length; i++) {
            strBuilder.append(params[i]);
        }
        return strBuilder.toString();
    }

    /**
     * Show Wrong Email Dialog
     */
    public static void showEmailErrorDialog(Context context,String msg) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_error);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TextView tv_dialog_login_error_msg = (TextView) dialog.findViewById(R.id.tv_dialog_login_error_msg);
        Button btn_dialog_login_error_ok = (Button) dialog.findViewById(R.id.btn_dialog_login_error_ok);
        tv_dialog_login_error_msg.setText(msg);
        dialog.show();
        btn_dialog_login_error_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
