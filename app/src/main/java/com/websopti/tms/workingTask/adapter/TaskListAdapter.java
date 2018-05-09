package com.websopti.tms.workingTask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.websopti.tms.R;
import com.websopti.tms.utility.ProjectUtils;
import com.websopti.tms.workingTask.constant.Constant;
import com.websopti.tms.workingTask.model.WorkingTaskModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TaskListAdapter extends ArrayAdapter<HashMap<String,List<WorkingTaskModel>>>  {

/*	private static final String TAG = TaskListAdapter.class.getSimpleName();
    private static final String CREATED = "Created";
    private static final String LAST_MODIFIED = "Last Modified";
    private static final String PRIORITY = "Priority";
    private static final String USER = "User";
    private static final String PROJECT = "Project";*/

	private Context context;
	private List<String> keyList;
    private List<Integer> keyListInteger;
	//private HashMap<String,List<WorkingTaskModel>> childDataMap;
    private String groupBy;
    private List<String> allRunningTaskList;
    private List<String> allPauseTaskList;
    private List<HashMap<String,List<WorkingTaskModel>>> listHashMap;

    /**
	 * Construction 
	 * @param context
	 */
	public TaskListAdapter(Context context,
						   int resource,
						   HashMap<String,List<WorkingTaskModel>> listChildData,
						   List<String> keyList,
                           List<String>allRunningTaskList,
                           List<String>allPauseTaskList,
                           List<HashMap<String,List<WorkingTaskModel>>> listHashMap) {
		super(context,resource,listHashMap);
		this.context = context;
       // this.childDataMap = listChildData;
        this.keyList = keyList;
        this.allRunningTaskList = allRunningTaskList;
        this.allPauseTaskList = allPauseTaskList;
        this.listHashMap = listHashMap;
        keyListInteger = new ArrayList<>();
	}

    @Override
	public View getView(final int position, View view, ViewGroup parent) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.task_sub_list, parent, false);
        }

        HashMap<String,List<WorkingTaskModel>> childDataMap = listHashMap.get(position);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ListView lv_sub_task_list = (ListView) view.findViewById(R.id.lv_sub_task_list);
        //Set Main Title
        if(keyList.size() > 0) {
            if (groupBy.equals(Constant.CREATED) || groupBy.equals(Constant.LAST_MODIFIED)) {
                //Set data after the sorting
                //Collections.sort(keyList);
                tv_title.setText(getCreatedTitle(keyList.get(position)));
            } else if (groupBy.equals(Constant.PRIORITY)) {
                //Set Data After The Sorting
                // Collections.sort(keyList);
                tv_title.setText(getPriorityTitle(keyList.get(position)));
            } else if (groupBy.equals(Constant.USER)) {
                //Set Data After The Sorting Data Take From The keyListInteger
                //holder.tv_title.setText(childDataMap.get(String.valueOf(keyListInteger.get(position))).get(0).getAssignToName());
                tv_title.setText(childDataMap.get(keyList.get(position)).get(0).getAssignToName());
            } else if (groupBy.equals(Constant.PROJECT)) {
                //Collections.sort(keyList);
                tv_title.setText(childDataMap.get(keyList.get(position)).get(0).getName());
            }


            //Set TaskSubList Adapter
            TaskSubListAdapter taskSubListAdapter = new TaskSubListAdapter(
                    this.context,
                    R.id.lv_sub_task_list,
                    childDataMap.get(keyList.get(position)),
                    allRunningTaskList,
                    allPauseTaskList,
                    keyList
            );

            lv_sub_task_list.setAdapter(taskSubListAdapter);
            //Set Height Of The ListView According To Child
            ProjectUtils.setListViewHeightBasedOnChildren(lv_sub_task_list);
        }
        return view;
	}

    /**
     * Get the task title by it's key
     * @param titleKey
     * @return
     */
    private String getPriorityTitle(String titleKey) {

        switch (titleKey) {

            case "0":
                return Constant.URGENT;
            case "1":
                return Constant.HIGH;
            case "2":
                return Constant.NORMAL;
            case "3":
                return Constant.LOW;
            default:
                return "";
        }
    }

    /**
     * Get the task title by it's key
     * @param titleKey
     * @return
     */
    private String getCreatedTitle(String titleKey) {

        switch (titleKey) {
            case "0":
                return Constant.TODAY;
            case "1":
                return Constant.YESTERDAY;
            case "2":
                return Constant.THIS_WEEK;
            case "3":
                return Constant.PREVIOUS_WEEK;
            case "4":
                return Constant.THIS_MONTH;
            case "5":
                return Constant.PREVIOUS_MONTH;
            case "6":
                return Constant.LATE;
            default:
                return "";
        }
    }

    /**
     * Notify Adapter
     * @param groupBy
     */
    public void notifyAdapter(String groupBy) {
        this.groupBy = groupBy;
        notifyDataSetChanged();
    }

    /**
     * Convert keyList To Integer keyList
     * @param mKeyList
     */
    public void copyAndSortKeyList(List<String> mKeyList) {

        if(keyListInteger != null)
            keyListInteger.clear();

        if(mKeyList != null && mKeyList.size() > 0) {
            for(int i=0; i < mKeyList.size(); i++)
            keyListInteger.add(Integer.parseInt(mKeyList.get(i)));
        }
        Collections.sort(keyListInteger);
    }
}
