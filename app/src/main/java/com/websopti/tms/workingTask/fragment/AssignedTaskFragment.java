package com.websopti.tms.workingTask.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.rest.ApiClient;
import com.websopti.tms.utility.DialogUtility;
import com.websopti.tms.utility.ProjectUtils;
import com.websopti.tms.utility.UserSession;
import com.websopti.tms.workingTask.adapter.TaskListAdapter;
import com.websopti.tms.workingTask.constant.Constant;
import com.websopti.tms.workingTask.model.WorkingTaskModel;
import com.websopti.tms.workingTask.rest.RunningTaskApiInterface;
import com.websopti.tms.workingTask.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rahul Padaliya on 9/15/2016.
 */
public class AssignedTaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AssignedTaskFragment.class.getSimpleName();
    public static final String ASSIGNED_TASK = "AssignedTask";
    private View view;
    /**
     * List view
     */
    private ListView lv_main_task_list;

    /**
     * Spinner
     */
    private Spinner spn_main_groupby;

    /**
     * Scroll View
     */
    private ScrollView src_tasklist_scrollview;

    /**
     * Text view
     */
    private TextView tv_working_task_emptyview;

    /**
     * Swipe Refresh View
     */
    private SwipeRefreshLayout swipe_refresh_view;

    private TaskListAdapter assignedTaskListAdapter;
    private UserSession userSession;

    /**
     * Hash Map For Data Child Of List
     */
    private HashMap<String, List<WorkingTaskModel>> taskChildMap;

    /**
     * List For Key
     */
    private List<String> keyList, allRunningTaskList, allPauseTaskList;

    /**
     * List Hash Map
     **/
    private List<HashMap<String,List<WorkingTaskModel>>> listHashMap;

    /**
     * Page Count And Total No
     */
    private int pageCount = 1, totalPageNo;

    /**
     * Set Spinner Item
     */
    private String spinnerItem = null;


    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null)
            view = inflater.inflate(R.layout.working_task_fragment, null);
        else
            ((ViewGroup) view.getParent()).removeView(view);

        //Initialization
        setUpView(view);
        userSession = new UserSession(getActivity());
        taskChildMap = new HashMap<>();
        keyList = new ArrayList<>();
        listHashMap = new ArrayList<>();
        allRunningTaskList = new ArrayList<>();
        allPauseTaskList = new ArrayList<>();

        //Set Adapter Of TaskList Adapter
        fillAssignedTaskList();
        fillGroupBySpinner();

        spn_main_groupby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reSetData();
                callAssignedTaskApi(pageCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        src_tasklist_scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                View view = (View) src_tasklist_scrollview.getChildAt(src_tasklist_scrollview.getChildCount() - 1);
                int diff = (view.getBottom() - (src_tasklist_scrollview.getHeight() + src_tasklist_scrollview.getScrollY()));

                // if diff is zero, then the bottom has been reached
                //Log.d(TAG,"Diff--->" + diff);
                if (diff == 0) {
                    if(pageCount <= totalPageNo) {
                        Log.i("Total Page No==>",""+totalPageNo);
                        Log.i("Page Count==>",""+pageCount);
                        callAssignedTaskApi(pageCount);
                    }
                }
            }
        });

        //Register listener
        swipe_refresh_view.setOnRefreshListener(this);

        return view;
    }

    /**
     * Initialize all view controller
     *
     * @param view
     */
    private void setUpView(View view) {

        //Scroll view
        src_tasklist_scrollview = (ScrollView) view.findViewById(R.id.src_tasklist_scrollview);

        /**
         * List view
         */
        lv_main_task_list = (ListView) view.findViewById(R.id.lv_main_task_list);

        /**
         * Spinner
         */
        spn_main_groupby = (Spinner) view.findViewById(R.id.spn_main_groupby);

        //Text view
        tv_working_task_emptyview = (TextView) view.findViewById(R.id.tv_working_task_emptyview);

        //Swipe Refresh View
         swipe_refresh_view = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
    }

    /**
     * Call Working Task Api
     */
    private void callAssignedTaskApi(final int page) {

        ++pageCount;
        Log.i("callAssignedTaskApi==>", ""+page);
        spinnerItem = spn_main_groupby.getSelectedItem().toString();
        if(spinnerItem.equals("Last Modified"))
            spinnerItem = "Last_Modified";

        final ProgressDialog dialog = DialogUtility.processDialog(getActivity(), com.websopti.tms.constant.Constant.PLEASE_WAIT_MESSAGE, false);
        RunningTaskApiInterface api = ApiClient.getRestAdapter().create(RunningTaskApiInterface.class);
        api.getRunningTaskList(
                Constant.ASSIGNED,
                userSession.getUserId(),
                String.valueOf(page),
                spinnerItem,
                new Callback<Response>() {
                    @Override
                    public void success(Response res, Response response) {
                        //dialog.hide();
                        Log.d(TAG, "Task List URL-->" + response.getUrl());
                        Log.i(TAG, ProjectUtils.getQueryString(
                                "Request Prameter-->",
                                "status="+Constant.ASSIGNED,
                                "&key="+userSession.getUserId(),
                                "&pageNum="+String.valueOf(page),
                                "&groupBy="+spinnerItem));
                        if (res != null) {
                            // Try to get response body
                            BufferedReader reader = null;
                            StringBuilder sb = new StringBuilder();
                            try {
                                reader = new BufferedReader(new InputStreamReader(res.getBody().in()));
                                String line;
                                try {
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Log.d(TAG, "Response---->" + sb.toString());
                                //Parse Response
                                parseJsonResponse(new JSONObject(sb.toString()));
                                dialog.hide();

                            } catch (IOException e) {
                                dialog.hide();
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.hide();
                        if (error != null) {
                            Log.d(TAG, "Login URL-->" + error.getUrl());
                            Log.i(TAG, ProjectUtils.getQueryString(
                                    "Request Parameter-->",
                                    "status="+Constant.ASSIGNED,
                                    "&key="+userSession.getUserId(),
                                    "&pageNum="+String.valueOf(page),
                                    "&groupBy="+spinnerItem));
                            if (error.getMessage() != null && error.getMessage().equals("502 Proxy Error")){
                                Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }
                            error.printStackTrace();
                            //Set Refreshing
                            swipe_refresh_view.setRefreshing(false);
                        }
                    }
                }
        );
    }

    /**
     * Parse Running Task Response
     * @param jsonObject
     */
    private void parseJsonResponse(JSONObject jsonObject) {

        taskChildMap = new HashMap<>();
        //keyList = new ArrayList<>();

        if (jsonObject != null) {
            try {

                JsonParser.getWorkingTaskMap(
                        jsonObject,
                        taskChildMap,
                        keyList,
                        listHashMap);

                JsonParser.allRunningTask(jsonObject, allRunningTaskList, ASSIGNED_TASK);
                JsonParser.allPauseTask(jsonObject, allPauseTaskList);

                //Set Count Of The Assigned Task
                JSONObject taskCount = jsonObject.getJSONObject("taskCount");
                RunningTaskFragment.setAssignedTabTitles(taskCount.getString(Constant.ASSIGNED));

                totalPageNo = Integer.valueOf(jsonObject.getString(Constant.TOTAL_PAGES));
                //Convert keyList To Integer keyList
                assignedTaskListAdapter.copyAndSortKeyList(keyList);
                assignedTaskListAdapter.notifyAdapter(spn_main_groupby.getSelectedItem().toString());
                swipe_refresh_view.setRefreshing(false);
                userSession.setGroupBy(spn_main_groupby.getSelectedItem().toString());
                ProjectUtils.setListViewHeightBasedOnChildren(lv_main_task_list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Clear required data
     * This is for add new data
     */
    private void reSetData() {
        pageCount = 1;
        taskChildMap.clear();
        keyList.clear();
        listHashMap.clear();
        allRunningTaskList.clear();
        allPauseTaskList.clear();
    }

    @Override
    public void onRefresh() {
        reSetData();
        callAssignedTaskApi(pageCount);
    }

    /**
     * Assigned task list
     * Create assigned task list adapter and set to list view
     */
    private void fillAssignedTaskList() {
        assignedTaskListAdapter = new TaskListAdapter(
                getActivity(),
                R.id.lv_main_task_list,
                taskChildMap,
                keyList,
                allRunningTaskList,
                allPauseTaskList,
                listHashMap);
        lv_main_task_list.setAdapter(assignedTaskListAdapter);
        lv_main_task_list.setEmptyView(tv_working_task_emptyview);
        ProjectUtils.setListViewHeightBasedOnChildren(lv_main_task_list);
    }

    /**
     * Group By Spinner
     * Create the adapter and set
     */
    private void fillGroupBySpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.row_spinner,
                R.id.tv_row_spinner_title,
                Constant.spinnerItemArray);
        spn_main_groupby.setAdapter(spinnerAdapter);
    }
}
