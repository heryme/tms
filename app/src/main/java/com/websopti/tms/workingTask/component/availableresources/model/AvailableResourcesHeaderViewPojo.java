package com.websopti.tms.workingTask.component.availableresources.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesChildViewPojo;

import java.util.List;

public class AvailableResourcesHeaderViewPojo implements Parent<AvailableResourcesChildViewPojo> {

    private String mName;
    private String count;
    private List<AvailableResourcesChildViewPojo> mAvailableResourcesChildViewPojos;

    public AvailableResourcesHeaderViewPojo(String name, String count, List<AvailableResourcesChildViewPojo> availableResourcesChildViewPojos) {
        mName = name;
        this.count = count;
        mAvailableResourcesChildViewPojos = availableResourcesChildViewPojos;
    }

    public String getName() {
        return mName;
    }

    public String getCount(){
        return count;
    }
    @Override
    public List<AvailableResourcesChildViewPojo> getChildList() {
        return mAvailableResourcesChildViewPojos;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public AvailableResourcesChildViewPojo getIngredient(int position) {
        return mAvailableResourcesChildViewPojos.get(position);
    }

    public boolean isVegetarian() {
        for (AvailableResourcesChildViewPojo availableResourcesChildViewPojo : mAvailableResourcesChildViewPojos) {
            if (!availableResourcesChildViewPojo.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
}
