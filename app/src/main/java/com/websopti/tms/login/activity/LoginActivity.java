package com.websopti.tms.login.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.activity.MainActivity;
import com.websopti.tms.login.constant.Constant;
import com.websopti.tms.login.model.LoginResponse;
import com.websopti.tms.login.rest.LoginApiInterface;
import com.websopti.tms.rest.ApiClient;
import com.websopti.tms.utility.DialogUtility;
import com.websopti.tms.utility.ProjectUtils;
import com.websopti.tms.utility.UserSession;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rahul Padaliya on 9/9/2016.
 */
public class LoginActivity  extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String EMAIL_ID = "Email_Id";

    /**
     * Edit Text
     */
    private EditText et_email,et_password;

    /**
     * Button
     */
    private Button btnLogin;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /**
         * Initialization
         */
        setUpView();
        userSession = new UserSession(LoginActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Check Validation Of The Edit Text  Value And  Check Email Validation
                 */
                if(ProjectUtils.checkEditTextValidation(LoginActivity.this, et_email, "Please Enter Email Id") &&
                    ProjectUtils.checkEditTextValidation(LoginActivity.this, et_password, "Please! Enter Password")) {
                    /**
                     * Get Login Response And Check Email Validation
                     */
                    if(ProjectUtils.isEmailValid(et_email.getText().toString().trim())) {
                        getLoginResponse();
                    } else {
                        //et_email.setError("Enter Valid Email Id");
                        //showEmailErrorDialog();
                        ProjectUtils.showEmailErrorDialog(LoginActivity.this,Constant.ERROR_MSG);
                    }
                }
            }
        });
    }
    /**
     * Initialize View
     */
    private void setUpView() {

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    /**
     * Get Login Response
     */
    private void getLoginResponse() {

        final ProgressDialog dialog =  DialogUtility.processDialog(LoginActivity.this,"Loading...!",false);
        LoginApiInterface api = ApiClient.getRestAdapter().create(LoginApiInterface.class);
        api.getLogin(
                et_email.getText().toString(),
                et_password.getText().toString(),
                new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {

                        if(loginResponse != null ) {

                            if(loginResponse.isSuccess()) {
                                Log.d(TAG,
                                        ProjectUtils.getQueryString(
                                        "Login URL-->",
                                        response.getUrl(),
                                        "email="+et_email.getText().toString(),
                                        "&password"+et_password.getText().toString()));

                                Log.d(TAG, "Success-->" + loginResponse.isSuccess());
                                Log.d(TAG, "Message-->" + loginResponse.getMessage());
                                Log.d(TAG, "UserId-->" + loginResponse.getUserId());

                                userSession.setUserId(loginResponse.getUserId());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(EMAIL_ID, et_email.getText().toString());
                                startActivity(intent);
                                Toast.makeText(getApplication(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            } else  {
                                Toast.makeText(getApplication(),"You are not authorise person"/* loginResponse.getMessage()*/, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(getApplication(),com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                        }
                        dialog.hide();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        dialog.hide();
                        if (error != null) {
                            Log.d(TAG,
                                    ProjectUtils.getQueryString(
                                            "Login URL-->",
                                            error.getUrl(),
                                            "email="+et_email.getText().toString(),
                                            "&password"+et_password.getText().toString()));

                            error.printStackTrace();
                            if (error.getMessage() != null && error.getMessage().equals("502 Proxy Error")){
                                Toast.makeText(getApplicationContext(),com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
