package com.websopti.tms.workingTask.utils;

import android.nfc.Tag;
import android.util.Log;

import com.websopti.tms.constant.Constant;
import com.websopti.tms.workingTask.fragment.RunningTaskFragment;
import com.websopti.tms.workingTask.fragment.WorkingTaskFragment;
import com.websopti.tms.workingTask.model.WorkingTaskModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rahul Padaliya on 11/21/2016.
 */
public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();
    /**
     * Parse Running Task Response
     * @param jsonObject
     */
    public static void getWorkingTaskMap(
            JSONObject jsonObject,
            HashMap<String, List<WorkingTaskModel>> taskChildMap,
            List<String> keyList,
            List<HashMap<String,List<WorkingTaskModel>>> listHashMap) {

        if (jsonObject != null) {
            try {

                JSONObject taskMapMainObject = jsonObject.getJSONObject(JsonConstant.TASK_MAP);
                Iterator<?> iterator = taskMapMainObject.keys();

                while (iterator.hasNext()) {

                    String key = (String) iterator.next();
                    JSONArray taskMapArray = taskMapMainObject.getJSONArray(key);

                    if (taskMapArray.length() > 0) {

                        List<WorkingTaskModel> taskList = new ArrayList<>();
                        for (int i = 0; i < taskMapArray.length(); i++) {

                            WorkingTaskModel workingTaskModel = new WorkingTaskModel();
                            workingTaskModel.setKey(key);
                            JSONObject taskMapSubObject = taskMapArray.getJSONObject(i);

                            if (taskMapSubObject.has(JsonConstant.CREATED_DATE)) {

                                String Date = taskMapSubObject.getString(JsonConstant.CREATED_DATE);
                                workingTaskModel.setDate(taskMapSubObject.getString(JsonConstant.CREATED_DATE));

                            } else {
                                workingTaskModel.setDate("");
                            }

                            workingTaskModel.setId(taskMapSubObject.getString(JsonConstant.ID));
                            JSONObject projectObject = taskMapSubObject.getJSONObject(JsonConstant.PROJECTS);
                            workingTaskModel.setName(projectObject.getString(JsonConstant.NAME));
                            workingTaskModel.setTitle(taskMapSubObject.getString(JsonConstant.TITLE));
                            workingTaskModel.setPriority(taskMapSubObject.getString(JsonConstant.PROJECTS_PRIORITY));
                            workingTaskModel.setTotalHours(taskMapSubObject.getString(JsonConstant.TOTAL_HOURS));
                            String expectedTimeInHours = taskMapSubObject.getString(JsonConstant.EXPECTED_TIME_IN_HOURS);
                            workingTaskModel.setExpectedTimeInHours(taskMapSubObject.getString(JsonConstant.EXPECTED_TIME_IN_HOURS));

                            if (taskMapSubObject.has(JsonConstant.ASSIGNED_TO) &&
                                    !taskMapSubObject.getString(JsonConstant.ASSIGNED_TO).equals("null")) {
                                if(taskMapSubObject.getJSONObject(JsonConstant.ASSIGNED_TO) != null) {
                                    JSONObject assignToObject = taskMapSubObject.getJSONObject(JsonConstant.ASSIGNED_TO);
                                    workingTaskModel.setAssignToName(assignToObject.getString(JsonConstant.NAME));
                                }
                            }
                            taskList.add(workingTaskModel);
                        }

                        // Data add according pagination first store in tempList for prevent overwrite
                        /*if (taskChildMap.get(key) != null) {
                            List<WorkingTaskModel> tempList = taskChildMap.get(key);
                            tempList.addAll(taskList);
                            taskChildMap.put(key, tempList);
                            listHashMap.add(taskChildMap);
                        } else {
                            taskChildMap.put(key, taskList);
                            listHashMap.add(taskChildMap);
                            keyList.add(key);
                        }*/
                        taskChildMap.put(key, taskList);
                        listHashMap.add(taskChildMap);
                        keyList.add(key);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all running task from response
     * @param jsonObject
     */
    public static void allRunningTask(JSONObject jsonObject, List<String> allRunningTaskList, String from) {

        if(jsonObject != null) {
            try {
                JSONArray allRunningTask = jsonObject.getJSONArray(JsonConstant.ALL_RUNNING_TASK);
                if(from.equals(WorkingTaskFragment.WORKING_TASK))
                    //Only Set Working Task Count Here
                    RunningTaskFragment.setWorkingTabTitle(String.valueOf(allRunningTask.length()));

                for (int i = 0; i < allRunningTask.length(); i++) {
                    allRunningTaskList.add(allRunningTask.getString(i));
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all pause task from response
     * @param jsonObject
     */
    public static void allPauseTask(JSONObject jsonObject, List<String> allPauseTaskList) {

        if(jsonObject != null) {
            try {
                JSONArray allPauseTask = jsonObject.getJSONArray(JsonConstant.ALL_PAUSED_TASK);
                for (int i = 0; i < allPauseTask.length(); i++) {
                    allPauseTaskList.add(allPauseTask.getString(i));
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
