package com.websopti.tms.workingTask.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.websopti.tms.R;
import com.websopti.tms.workingTask.adapter.ViewPageAdapter;

/**
 * Created by Rahul Padaliya on 9/13/2016.
 */
public class RunningTaskFragment extends Fragment {

    private static final String TAG = RunningTaskFragment.class.getSimpleName();
    private static TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null)
            view = inflater.inflate(R.layout.running_task_fragment, null);
        else
            ((ViewGroup) view.getParent()).removeView(view);


        //Initialization
        setUpView(view);
        setupTabIcons();
        setViewPageAdapter();


        return view ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }*/

    /**
     * Initialize View
     * @param view
     */
    private void setUpView(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
    }

    /**
     *Set Viewpager Adapter
     */
    private void setViewPageAdapter(){
        viewPager.setAdapter(new ViewPageAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    /**
     * Set Up Icons
     */
    private void setupTabIcons() {
         int[] tabIcons = {
                 R.mipmap.ic_working,
                 R.mipmap.ic_over_due,
                 R.mipmap.ic_assign,
                 R.mipmap.ic_available_resources
         };

        tabLayout.addTab(tabLayout.newTab().setText("Working").setIcon(tabIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setText("Over Due").setIcon(tabIcons[1]));
        tabLayout.addTab(tabLayout.newTab().setText("Assigned").setIcon(tabIcons[2]));
        tabLayout.addTab(tabLayout.newTab().setText("Available Resources").setIcon(tabIcons[3]));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    /**
     * Set Title Of The Working tab With Count
     * @param count
     */
    public static void setWorkingTabTitle(String count){
        tabLayout.getTabAt(0).setText("Working" + " "+ "(" +count + ")");
    }

    //Set Title Of The OverDue Tab With Count
    public static void setOverDueTabTitles(String count){
        tabLayout.getTabAt(1).setText("Over Due" + " "+ "(" +count + ")");
    }

    //Set Title Of The Assigned Tab With Count
    public static void setAssignedTabTitles(String count){
        tabLayout.getTabAt(2).setText("Assigned" + " "+ "(" +count + ")");
    }
}

