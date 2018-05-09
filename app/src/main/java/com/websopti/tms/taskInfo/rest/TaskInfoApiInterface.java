package com.websopti.tms.taskInfo.rest;

import com.websopti.tms.workingTask.model.AvailableResourceModel;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Rahul Padaliya on 11/15/2016.
 */
public interface TaskInfoApiInterface {

    //http://192.168.1.11:8090/api/{taskId}/{key}
    @GET("/{taskId}/{key}")
    void getTaskInfo(
            @Path("taskId") String taskId,
            @Path("key") String key,
            Callback<Response> response);
}
