package com.websopti.tms.workingTask.component.availableresources.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.websopti.tms.R;
import com.websopti.tms.rest.ApiClient;
import com.websopti.tms.utility.DialogUtility;
import com.websopti.tms.utility.UserSession;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesChildViewPojo;
import com.websopti.tms.workingTask.component.availableresources.model.AvailableResourcesHeaderViewPojo;
import com.websopti.tms.workingTask.component.availableresources.adapter.AvailableResourceAdapter;
import com.websopti.tms.workingTask.model.AvailableResourceModel;
import com.websopti.tms.workingTask.rest.AvailableResourceApiInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Sample Activity for the vertical linear RecyclerView sample.
 * Uses ButterKnife to inject view resources.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class NewAvailableResourcesFragment extends Fragment {

    private static final String TAG = NewAvailableResourcesFragment.class.getSimpleName();

    /**
     * RecyclerView
     */
    private RecyclerView recyclerView;
    /**
     * List For Header
     */
    private List<AvailableResourceModel.ResourceDetail> listDataHeader;

    /**
     * Adapter
     */
    private AvailableResourceAdapter mAdapter;
    private View rootView;
    private UserSession userSession;

    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, NewAvailableResourcesFragment.class);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null)
            rootView = inflater.inflate(R.layout.activity_recycler_view_sample, null);
        else
            ((ViewGroup) rootView.getParent()).removeView(rootView);

        //Initialization
        userSession = new UserSession(getActivity());
        listDataHeader = new ArrayList<>();
        initViewController();
        getAvailableResource();

        /*AvailableResourcesChildViewPojo beef = new AvailableResourcesChildViewPojo("beef", true);
        AvailableResourcesChildViewPojo cheese = new AvailableResourcesChildViewPojo("cheese", true);
        AvailableResourcesChildViewPojo salsa = new AvailableResourcesChildViewPojo("salsa", true);
        AvailableResourcesChildViewPojo tortilla = new AvailableResourcesChildViewPojo("tortilla", true);
        AvailableResourcesChildViewPojo ketchup = new AvailableResourcesChildViewPojo("ketchup", true);
        AvailableResourcesChildViewPojo bun = new AvailableResourcesChildViewPojo("bun", true);

        AvailableResourcesHeaderViewPojo taco = new AvailableResourcesHeaderViewPojo("taco","1", Arrays.asList(beef, cheese, salsa, tortilla));
        AvailableResourcesHeaderViewPojo quesadilla = new AvailableResourcesHeaderViewPojo("quesadilla","2", Arrays.asList(cheese, tortilla));
        AvailableResourcesHeaderViewPojo burger = new AvailableResourcesHeaderViewPojo("burger","3", Arrays.asList(beef, cheese, ketchup, bun));
        AvailableResourcesHeaderViewPojo Rahul = new AvailableResourcesHeaderViewPojo("Rahul","4", Arrays.asList(beef, cheese, ketchup, bun));
        final List<AvailableResourcesHeaderViewPojo> availableResourcesHeaderViewPojos = Arrays.asList(taco, quesadilla, burger, Rahul);
*/
        /*mAdapter = new AvailableResourceAdapter(getActivity(), availableResourcesHeaderViewPojos);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                AvailableResourcesHeaderViewPojo expandedRecipe = availableResourcesHeaderViewPojos.get(parentPosition);

                String toastMsg = "Expand";
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                AvailableResourcesHeaderViewPojo collapsedRecipe = availableResourcesHeaderViewPojos.get(parentPosition);

                String toastMsg = "Collapsed";//getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        return rootView;
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
                            //mapChild.put(resource.getName(),resource.getAssignedTaskListDetailsList());
                        }
                        fillHeader();
                        //availableResourceAdapter.notifyDataSetChanged();
                        //swipe_refresh_view.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dialog.dismiss();
                        if(error != null) {
                            Log.i(TAG,"Available Resource URL ::"+error.getUrl());
                            Log.i(TAG, error.toString());
                            if (error.getMessage() != null && error.getMessage().equals("502 Proxy Error")){
                                Toast.makeText(getContext(),com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(),com.websopti.tms.constant.Constant.TOAST_SERVER_NOT_RESPONDING,Toast.LENGTH_SHORT).show();
                            }
                            error.printStackTrace();
                            //Set Refreshing False
                            //swipe_refresh_view.setRefreshing(false);
                        }
                    }
                }
        );
    }

    /**
     * Initialize all view controller
     */
    private void initViewController() {
        //RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
    }

    /**
     * Fill The Header View User Name And Task Count
     */
    private void fillHeader() {

        final List<AvailableResourcesHeaderViewPojo> availableResourcesHeaderViewPojoList = new ArrayList<>();
        if(listDataHeader != null && listDataHeader.size() > 0) {

            for(int i=0; i<listDataHeader.size(); i++) {
                List<AvailableResourcesChildViewPojo> availableResourcesChildViewPojoList = new ArrayList<>();
                AvailableResourceModel.ResourceDetail resourceDetail = listDataHeader.get(i);
                List<AvailableResourceModel.ResourceDetail.AssignedTaskListDetails> assignedTaskListDetailses = resourceDetail.getAssignedTaskListDetailsList();
                if(assignedTaskListDetailses != null && assignedTaskListDetailses.size() > 0) {
                    for (int j = 0; j < assignedTaskListDetailses.size(); j++) {
                        AvailableResourceModel.ResourceDetail.AssignedTaskListDetails assignedTaskListDetails = assignedTaskListDetailses.get(j);
                        //AvailableResourcesChildViewPojo taskDetails = new AvailableResourcesChildViewPojo("Title:- "+i/*assignedTaskListDetails.getTitle()*/,true);
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm a");
                        AvailableResourcesChildViewPojo taskDetails = new AvailableResourcesChildViewPojo(assignedTaskListDetails.getTitle(),assignedTaskListDetails.getProject().getName(),dateFormat.format(Long.parseLong(assignedTaskListDetails.getCreatedDate())));
                        availableResourcesChildViewPojoList.add(taskDetails);
                    }
                }
                //Pass User Name And Task Count Here.
                AvailableResourcesHeaderViewPojo availableResourcesHeaderViewPojo = new AvailableResourcesHeaderViewPojo(resourceDetail.getName(),resourceDetail.getTaskCount(), availableResourcesChildViewPojoList);
                availableResourcesHeaderViewPojoList.add(availableResourcesHeaderViewPojo);
            }
        }
        //Set Adapter
        mAdapter = new AvailableResourceAdapter(getActivity(), availableResourcesHeaderViewPojoList);
        fillAdapter();
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                AvailableResourcesHeaderViewPojo expandedAvailableResourcesHeaderViewPojo = availableResourcesHeaderViewPojoList.get(parentPosition);

               /* String toastMsg = "Expand";
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();*/
            }
            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                AvailableResourcesHeaderViewPojo collapsedAvailableResourcesHeaderViewPojo = availableResourcesHeaderViewPojoList.get(parentPosition);

               /* String toastMsg = "Collapsed";//getResources().getString(R.string.collapsed, collapsedAvailableResourcesHeaderViewPojo.getName());
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();*/
            }
        });
    }

    /**
     * Fill Adapter
     */
    private void fillAdapter() {
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
