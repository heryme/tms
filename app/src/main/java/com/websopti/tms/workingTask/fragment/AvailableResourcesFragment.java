package com.websopti.tms.workingTask.fragment;

import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.websopti.tms.R;
import com.websopti.tms.rest.ApiClient;
import com.websopti.tms.utility.DialogUtility;
import com.websopti.tms.utility.UserSession;
import com.websopti.tms.workingTask.adapter.AvailableResourceAdapter;
import com.websopti.tms.workingTask.model.AvailableResourceModel;
import com.websopti.tms.workingTask.rest.AvailableResourceApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rahul Padaliya on 9/15/2016.
 */
public class AvailableResourcesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AvailableResourcesFragment.class.getSimpleName();

    /**
     * View
     */
    private View rootView;

    /**
     * Expandable ListView
     */
    private ExpandableListView lv_available_res;

    /**
     * Swipe Refresh View
     */
    private SwipeRefreshLayout swipe_refresh_view;

    /**
     * SharePreferences
     */
    private UserSession userSession;

    /**
     * List For Header
     */
    private List<AvailableResourceModel.ResourceDetail> listDataHeader;

    /**
     * Adapter
     */
    private AvailableResourceAdapter availableResourceAdapter;

    /**
     * Hash map For Child
     */
    private HashMap<String, List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails>> mapChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null)
            rootView = inflater.inflate(R.layout.available_res_fragment, null);
        else
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        /**
         * Initialization
         */
        /*initViewController();
        userSession = new UserSession(getActivity());
        fillAvailableResourceList();
        getAvailableResource();*/

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Initialization
         */
        initViewController();
        userSession = new UserSession(getActivity());
        fillAvailableResourceList();
        getAvailableResource();

    }


    /**
     * Initialize all view controller
     */
    private void initViewController() {

        //List view
        lv_available_res = (ExpandableListView) rootView.findViewById(R.id.lv_available_res);

        //Swipe Refresh View
        swipe_refresh_view = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
    }

    /**
     * Fill available resource list adapter
     */
    private void fillAvailableResourceList() {

        listDataHeader = new ArrayList<>();
        mapChild = new HashMap<>();
        availableResourceAdapter = new AvailableResourceAdapter(
                getActivity(),
                listDataHeader,
                mapChild);

        lv_available_res.setAdapter(availableResourceAdapter);
        //Set Swipe Refresh View Listener
        swipe_refresh_view.setOnRefreshListener(this);
    }

    /**
     *Get available resource
     */
    private void getAvailableResource() {

        final ProgressDialog dialog = DialogUtility.processDialog(getActivity(), "Loading...!", false);
        AvailableResourceApiInterface api = ApiClient.getRestAdapter().create(AvailableResourceApiInterface.class);

        api.getAvailableResource(
                userSession.getUserId(),
                new Callback<AvailableResourceModel>() {
                    @Override
                    public void success(AvailableResourceModel resourceDetail, Response response) {
                        Log.i(TAG,"Available Resource URL ::"+response.getUrl());
                        dialog.dismiss();
                        //resourceDetails.addAll(resourceDetail.getAvailableResources());
                        for (int i=0; i<resourceDetail.getAvailableResources().size(); i++) {
                            AvailableResourceModel.ResourceDetail resource = resourceDetail.getAvailableResources().get(i);
                            listDataHeader.add(resource);
                            mapChild.put(resource.getName(),resource.getAssignedTaskListDetailsList());

                        }
                        availableResourceAdapter.notifyDataSetChanged();
                        swipe_refresh_view.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.dismiss();
                        if(error != null) {
                            Log.i(TAG,"Available Resource URL ::"+error.getUrl());
                            Log.i(TAG, error.toString());
                            if (error.getMessage() != null && error.getMessage().equals("502 Proxy Error")){
                                Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }
                            error.printStackTrace();
                            //Set Refreshing False
                            swipe_refresh_view.setRefreshing(false);
                        }
                    }
                }
        );
    }

    @Override
    public void onRefresh() {
        getAvailableResource();
    }

}
