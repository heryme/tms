package com.websopti.tms.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.login.activity.LoginActivity;
import com.websopti.tms.utility.UserSession;
import com.websopti.tms.workingTask.fragment.RunningTaskFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EMAIL_ID = "Email_Id";

    /**
     * Drawer Layout
     */
    private DrawerLayout drawerLayout;

    /**
     * Toolbar
     */
    private Toolbar toolbar;

    /**
     * Sharedpreferences
     */
    private UserSession userSession;

    /**
     * NavigationView
     */
    private NavigationView navigationView;

    /**
     * ActionBarDrawerToggle
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * Title List
     */
    private ArrayList<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Initialization
        userSession = new UserSession(getApplicationContext());
        titleList = new ArrayList<>();

        //Set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Display Back Arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Not Set By Default Title Ex:By Default Set App Name :TMS
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Initialization Drawer
        initNavigationDrawer();
        //Back Press Event Of The Back Arrow
        final View.OnClickListener toolbarListener = actionBarDrawerToggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d(TAG, "On Back Entry On Back Arrow =" + getSupportFragmentManager().getBackStackEntryCount());
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fragmentHandling();
                        }
                    });
                } else {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(toolbarListener);
                }
            }
        });

        loadFragment(new RunningTaskFragment(), false, "Running Task");
    }

    /**
     * Initialize Navigation Drawer
     */
    public void initNavigationDrawer() {

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){

                    //Running Task Response
                    case R.id.Running_Task:
                        loadFragment(new RunningTaskFragment(), true, "Running Task");
                        break;

                    case R.id.Project_Task:
                        Toast.makeText(getApplicationContext(),"Project Task",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.Task_Information:
                        Toast.makeText(getApplicationContext(),"Task Information",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.logout:
                        //Call Logout Method
                        logout();
                        finish();
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        //Header View Display For Logged User
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        Intent intent = getIntent();
        Log.d(TAG,"Email Id-->" + intent.getStringExtra(EMAIL_ID));
        tv_email.setText(intent.getStringExtra(EMAIL_ID));
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

         actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Logout
     */
    private void logout() {

        userSession.setUserId("");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Load Fragment
     * @param fragment
     * @param isMenuItem
     */
    public void loadFragment(Fragment fragment, boolean isMenuItem,String title) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentArea, fragment, fragment.getClass().getSimpleName());
        if(isMenuItem){
            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0;i < fm.getBackStackEntryCount();i++){
                Log.d(TAG,"Back Stack Entry--->" + getFragmentManager().getBackStackEntryCount());
                //Clear Fragment Back Stack
                fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                titleList.clear();
            }
        }
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
        titleList.add(title);
        toolbar.setTitle(title);
        Log.d(TAG,"Toolbar Title -->" + toolbar.getTitle());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d(TAG, "On Back Entry Count =" + getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            showExitDialog();
        } else {
            fragmentHandling();
        }
    }

    private void fragmentHandling() {
             //Fragment Remove From The Stack
            getSupportFragmentManager().popBackStack();
            //Size Decrease Of The Title List
            if (titleList.size() > 0)
                titleList.remove(titleList.size() - 1);

            //Set ToolBar Title According To Title List
            if (titleList.size() > 0)
                toolbar.setTitle(titleList.get(titleList.size() - 1));
    }

    /**
     * Show Exit Dialog
     */
    private void showExitDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_application_exit);
        dialog.setCancelable(false);
        Button btn_dialog_app_no = (Button) dialog.findViewById(R.id.btn_dialog_app_no);
        Button btn_dialog_app_yes = (Button) dialog.findViewById(R.id.btn_dialog_app_yes);
        dialog.show();
        btn_dialog_app_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_dialog_app_yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
               System.exit(0);
            }
       });
    }
}
