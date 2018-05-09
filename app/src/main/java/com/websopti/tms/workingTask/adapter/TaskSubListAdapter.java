package com.websopti.tms.workingTask.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.activity.MainActivity;
import com.websopti.tms.taskInfo.fragment.TaskInfoFragment;
import com.websopti.tms.utility.UserSession;
import com.websopti.tms.workingTask.constant.Constant;
import com.websopti.tms.workingTask.model.WorkingTaskModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahul Padaliya on 9/20/2016.
 */
public class TaskSubListAdapter extends ArrayAdapter<WorkingTaskModel> {

    private static final String TAG = TaskSubListAdapter.class.getSimpleName();
    private UserSession userSession;
    private static List<WorkingTaskModel> workingTaskitemList;
    private List<String> allRunningTaskList;
    private List<String> allPauseTaskList;
    public TaskSubListAdapter(
            Context context,
            int resource,
            List<WorkingTaskModel> workingTaskitemList,
            List<String> allRunningTaskList,
            List<String> allPauseTaskList,
            List<String> key) {
        super(context, resource, workingTaskitemList);
        this.workingTaskitemList = workingTaskitemList;
        this.allRunningTaskList = allRunningTaskList;
        this.allPauseTaskList = allPauseTaskList;
        //Initialization
        userSession = new UserSession(getContext());
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.row_task_sub_list, parent, false);
        }
        TextView tv_task_title = (TextView) view.findViewById(R.id.tv_task_title);
        TextView tv_task_project_name = (TextView) view.findViewById(R.id.tv_task_project_name);
        TextView tv_assignTo = (TextView) view.findViewById(R.id.tv_assignTo);
        TextView tv_hours = (TextView) view.findViewById(R.id.tv_hours);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_date);

        /**
         * ImageView
         */
        ImageView iv_task_priority = (ImageView) view.findViewById(R.id.iv_task_priority);
        iv_task_priority.setImageResource(R.mipmap.ic_star);

        ImageView iv_assignTo = (ImageView) view.findViewById(R.id.iv_assignTo);
        iv_assignTo.setImageResource(R.mipmap.ic_assign_to);

        ImageView iv_projectName = (ImageView) view.findViewById(R.id.iv_projectName);
        iv_projectName.setImageResource(R.mipmap.ic_project);

        ImageView iv_task_info = (ImageView) view.findViewById(R.id.iv_task_info);
        iv_task_info.setImageResource(R.mipmap.ic_eye);


        //Instant Of The WorkingTaskModel
        final WorkingTaskModel workingTaskModel = getItem(position);
        //Set Priority
        if (workingTaskModel.getPriority().equalsIgnoreCase(Constant.URGENT)) {
            iv_task_priority.setColorFilter(ContextCompat.getColor(getContext(), R.color.urgent));
        } else if (workingTaskModel.getPriority().equalsIgnoreCase(Constant.HIGH)) {
            iv_task_priority.setColorFilter(ContextCompat.getColor(getContext(), R.color.high));
        } else if (workingTaskModel.getPriority().equalsIgnoreCase(Constant.NORMAL)) {
            iv_task_priority.setColorFilter(ContextCompat.getColor(getContext(), R.color.normal));
        } else if (workingTaskModel.getPriority().equalsIgnoreCase(Constant.LOW)) {
            iv_task_priority.setColorFilter(ContextCompat.getColor(getContext(), R.color.low));
        }

        //Set Project Title
        tv_task_title.setText(workingTaskModel.getTitle());

        //Set Project Name
        if (userSession.getSpinnerItem().equalsIgnoreCase(Constant.PROJECT)) {
            tv_task_project_name.setVisibility(View.GONE);
            iv_projectName.setVisibility(View.GONE);
        } else {
            tv_task_project_name.setText(workingTaskModel.getName());
        }

        //Set Assign To The Name
        if (userSession.getSpinnerItem().equals(Constant.USER)) {
            tv_assignTo.setVisibility(View.GONE);
            iv_assignTo.setVisibility(View.GONE);
        } else {
            if (workingTaskModel.getAssignToName() != null) {
                tv_assignTo.setText(workingTaskModel.getAssignToName());
                //System.out.println("Assign To Name--->" + workingTaskModel.getAssignToName());
            } else {
                tv_assignTo.setText("Not Assigned");
            }
        }
        //Set Total Hours And Expected Hours
        if (workingTaskModel.getTotalHours().equals("null")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0");
            stringBuilder.append("/");
            stringBuilder.append(workingTaskModel.getExpectedTimeInHours());
            tv_hours.setText(stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(workingTaskModel.getTotalHours());
            stringBuilder.append("/");
            stringBuilder.append(workingTaskModel.getExpectedTimeInHours());
            tv_hours.setText(stringBuilder.toString());
        }

        //TODO: Comment out below line for date issue in live and local Line No 135
        //Nov 19, 2016 12:20:40 PM
        if (workingTaskModel.getDate() != null && workingTaskModel.getDate().length() > 0) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = formatter.format(new Date(Long.parseLong(workingTaskModel.getDate())));
                tv_date.setText(/*formatter.format(new Date(workingTaskModel.getDate()))*/dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tv_date.setText("");
        }

        //Set Row Color For Working Running Task
        if(allRunningTaskList.contains(workingTaskModel.getId())) {
            //view.setBackgroundColor(Color.parseColor("#9FD878"));
            view.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.all_running_task));
        }

        //Set Row Color For All Pause Task
        if(allPauseTaskList.contains(workingTaskModel.getId())) {
            // view.setBackgroundColor(Color.parseColor("#F0AD4E"));
            view.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.all_pause_task));
        }

        //View Get Task Information
        iv_task_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("TaskId",workingTaskModel.getId());
                TaskInfoFragment taskInfoFragment = new TaskInfoFragment();
                taskInfoFragment.setArguments(bundle);
                ((MainActivity)getContext()).loadFragment(taskInfoFragment,false,"Task Info");
            }
        });
        return view;
    }
}
