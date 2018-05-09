package com.websopti.tms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.websopti.tms.R;
import com.websopti.tms.login.activity.LoginActivity;
import com.websopti.tms.utility.UserSession;

/**
 * Created by Rahul Padaliya on 9/12/2016.
 */
public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //Initialization
        userSession = new UserSession(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Check User Ready Login Or Not
                if(userSession.getUserId().isEmpty()) {

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    finish();
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        }, 1000);
    }
}
