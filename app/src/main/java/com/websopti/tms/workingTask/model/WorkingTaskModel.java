package com.websopti.tms.workingTask.model;

/**
 * Created by Rahul Padaliya on 9/13/2016.
 */
public class WorkingTaskModel {

    private String id;
    private String name;
    private String title;
    private String key;
    private String priority;
    private String assignToName;
    private String expectedTimeInHours;
    private String totalHours;
    private String Date;

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
    public String getExpectedTimeInHours() {
        return expectedTimeInHours;
    }

    public void setExpectedTimeInHours(String expectedTimeInHours) {
        this.expectedTimeInHours = expectedTimeInHours;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignToName() {
        return assignToName;
    }

    public void setAssignToName(String assignToName) {
        this.assignToName = assignToName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
