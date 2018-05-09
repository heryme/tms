package com.websopti.tms.workingTask.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.websopti.tms.workingTask.component.availableresources.fragment.NewAvailableResourcesFragment;
import com.websopti.tms.workingTask.fragment.AssignedTaskFragment;
import com.websopti.tms.workingTask.fragment.OverDueTaskFragment;
import com.websopti.tms.workingTask.fragment.WorkingTaskFragment;

/**
 * Created by Rahul Padaliya on 9/27/2016.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 : return new WorkingTaskFragment();
            case 1 : return new OverDueTaskFragment();
            case 2 : return new AssignedTaskFragment();
            case 3 : return new NewAvailableResourcesFragment();
        }
        return null;
    }

   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {

        FragmentTransaction ft = fm.beginTransaction();

        switch (position){
            case 0 :
                ft.add(container.getId(), new WorkingTaskFragment());
                break;
                //return new WorkingTaskFragment();
            case 1 :
                ft.add(container.getId(), new OverDueTaskFragment());
                //return new OverDueTaskFragment();
                break;
            case 2 :
                ft.add(container.getId(), new OverDueTaskFragment());
                        break;
                return new AssignedTaskFragment();
            case 3 : return new AvailableResourcesFragment();
        }
        return null;
        //return super.instantiateItem(container, position);
    }*/

   /* @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }*/

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "Working";
            case 1 :
                return "Over Due";
            case 2 :
                return "Assigned";
            case 3 :
                return "Available Resources";
        }
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {}

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;  // This will get invoke as soon as you call notifyDataSetChanged on viewPagerAdapter.
    }
}
