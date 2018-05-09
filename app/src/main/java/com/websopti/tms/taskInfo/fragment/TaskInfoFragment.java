package com.websopti.tms.taskInfo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.rest.ApiClient;
import com.websopti.tms.taskInfo.constant.Constant;
import com.websopti.tms.taskInfo.rest.TaskInfoApiInterface;
import com.websopti.tms.utility.DialogUtility;
import com.websopti.tms.utility.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Rahul Padaliya on 11/14/2016.
 */
public class TaskInfoFragment extends Fragment{

    private static final String TAG = TaskInfoFragment.class.getSimpleName();

    /**
     * TextView
     */
    private TextView tv_task_info_fragment_task_id,tv_task_info_fragment_assigned_to,
            tv_task_info_fragment_assigned_by,tv_task_info_fragment_project,
            tv_task_info_fragment_type_of_task,tv_task_info_fragment_task_status,
            tv_task_info_fragment_task_details,tv_task_info_fragment_exp_time_hours,
            tv_task_info_fragment_date_created,tv_task_info_fragment_last_edited,
            tv_task_info_fragment_last_action_date,tv_task_info_fragment_last_edited_task;
    /**
     * ImageView
     */
    private ImageView iv_task_info_fragment_last_edited_task;

    /**
     * LinearLayout
     */
    private LinearLayout ll_last_edited_task;
    private Bundle bundle;
    private UserSession userSession;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.get_task_info_fragment, null);

        /**
         * Initialization
         */
        setUpView(view);
        bundle = this.getArguments();
        userSession = new UserSession(getActivity());
        getTaskInformation();
        return view;
    }

    /**
     * Initialize all view controller
     * @param view
     */
    private void setUpView(View view) {

        //TextView
        tv_task_info_fragment_task_id = (TextView)view.findViewById(R.id.tv_task_info_fragment_task_id);
        tv_task_info_fragment_assigned_to = (TextView)view.findViewById(R.id.tv_task_info_fragment_assigned_to);
        tv_task_info_fragment_assigned_by = (TextView)view.findViewById(R.id.tv_task_info_fragment_assigned_by);
        tv_task_info_fragment_project = (TextView)view.findViewById(R.id.tv_task_info_fragment_project);
        tv_task_info_fragment_type_of_task = (TextView)view.findViewById(R.id.tv_task_info_fragment_type_of_task);
        tv_task_info_fragment_task_status = (TextView)view.findViewById(R.id.tv_task_info_fragment_task_status);
        tv_task_info_fragment_task_details = (TextView)view.findViewById(R.id.tv_task_info_fragment_task_details);
        tv_task_info_fragment_exp_time_hours = (TextView)view.findViewById(R.id.tv_task_info_fragment_exp_time_hours);
        tv_task_info_fragment_date_created = (TextView)view.findViewById(R.id.tv_task_info_fragment_date_created);
        tv_task_info_fragment_last_edited = (TextView)view.findViewById(R.id.tv_task_info_fragment_last_edited);
        tv_task_info_fragment_last_action_date = (TextView)view.findViewById(R.id.tv_task_info_fragment_last_action_date);
        tv_task_info_fragment_last_edited_task = (TextView)view.findViewById(R.id.tv_task_info_fragment_last_edited_task);

        //ImageView
        iv_task_info_fragment_last_edited_task = (ImageView)view.findViewById(R.id.iv_task_info_fragment_last_edited_task);

        //LinearLayout
        ll_last_edited_task = (LinearLayout)view.findViewById(R.id.ll_last_edited_task);
    }

    /**
     * Get the current selected task information
     */
    private void getTaskInformation() {
        final ProgressDialog progressDialog = DialogUtility.processDialog(getActivity(),"Please wait...", false);
        TaskInfoApiInterface api = ApiClient.getRestAdapter().create(TaskInfoApiInterface.class);
        api.getTaskInfo(
                bundle.getString("TaskId"),
                userSession.getUserId(),
                new Callback<Response>() {
                    @Override
                    public void success(Response res, Response response) {
                        progressDialog.dismiss();
                        Log.d(TAG,"URL-->" + res.getUrl());
                        if (res != null) {

                            BufferedReader reader = null;
                            StringBuilder sb = new StringBuilder();

                            try {

                                reader = new BufferedReader(new InputStreamReader(res.getBody().in()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                }

                                Log.d(TAG, "Task Info Response---->" + sb.toString());
                                parseTaskInfoResponse(new JSONObject(sb.toString()));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void failure(RetrofitError error) {
                        progressDialog.dismiss();
                        if(error != null) {
                            error.printStackTrace();
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
        });
    }

    //Parse Task Info Response
    private void parseTaskInfoResponse(JSONObject jsonObject) {

        if(jsonObject != null){

            try {
                JSONObject mainJsonObject = jsonObject.getJSONObject(Constant.TASK);
                String createdDate = "";
                if(mainJsonObject.has(Constant.CREATED_DATE))
                    createdDate = mainJsonObject.getString(Constant.CREATED_DATE);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mm a");
                //TODO: Comment out below code for date issue in live Line No 171
                if(createdDate != null && createdDate.length() > 0) {
                    //Set Date Created
                     tv_task_info_fragment_date_created.setText(formatter.format(new Date(Long.parseLong(createdDate))));
                    //tv_task_info_fragment_date_created.setText(formatter.format(createdDate));
                } else {
                    tv_task_info_fragment_date_created.setText(createdDate);
                }

                //Set Last Edited Task
                if(mainJsonObject.has(Constant.MODIFIED_DATE) && mainJsonObject.getString(Constant.MODIFIED_DATE) != null)
                    tv_task_info_fragment_last_action_date.setText(formatter.format(new Date(Long.parseLong( mainJsonObject.getString("modifiedDate")))));
                    tv_task_info_fragment_task_id.setText(mainJsonObject.getString(Constant.ID));

                //Set Project
                JSONObject project = mainJsonObject.getJSONObject(Constant.PROJECT);
                tv_task_info_fragment_project.setText(project.getString(Constant.NAME));
                tv_task_info_fragment_task_details.setText((Html.fromHtml(mainJsonObject.getString(Constant.DESCRIPTION))));
                if(!mainJsonObject.getString(Constant.ASSIGNED_TO).equals("null")){
                    JSONObject assignedTo = mainJsonObject.getJSONObject(Constant.ASSIGNED_TO);
                    tv_task_info_fragment_assigned_to.setText(assignedTo.getString(Constant.NAME));
                }

                //Set Set Assigned By Name
                JSONObject assignedBy = mainJsonObject.getJSONObject(Constant.ASSIGNED_BY);
                tv_task_info_fragment_assigned_by.setText(assignedBy.getString(Constant.NAME));
                tv_task_info_fragment_task_status.setText(mainJsonObject.getString(Constant.STATUS));
                tv_task_info_fragment_type_of_task.setText(mainJsonObject.getString(Constant.TASK_TYPE));
                tv_task_info_fragment_exp_time_hours.setText( mainJsonObject.getString(Constant.EXPECTED_TIME_IN_HOURS));

                //Set Last Action Date
                if(!mainJsonObject.getString(Constant.TASK_EDITED_DATE).equals("null")){
                    tv_task_info_fragment_last_edited.setText(formatter.format(new Date(Long.parseLong( mainJsonObject.getString(Constant.TASK_EDITED_DATE)))));
                } else {
                    /*tv_task_info_fragment_last_edited.setVisibility(View.GONE);
                    tv_task_info_fragment_last_edited_task.setVisibility(View.GONE);
                    iv_task_info_fragment_last_edited_task.setVisibility(View.GONE);*/
                    ll_last_edited_task.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
