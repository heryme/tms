package com.websopti.tms.workingTask.component.availableresources.model;

public class AvailableResourcesChildViewPojo {

    private String mName;
    private boolean mIsVegetarian;


    private String title;
    private String projectName;
    private String createdDate;

    public AvailableResourcesChildViewPojo(
            String title,
            String projectName,
            String createdDate) {

        this.title = title;
        this.projectName = projectName;
        this.createdDate = createdDate;

    }

    public AvailableResourcesChildViewPojo(String name, boolean isVegetarian) {
        mName = name;
        mIsVegetarian = isVegetarian;

    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return mName;
    }

    public boolean isVegetarian() {
        return mIsVegetarian;
    }
}
