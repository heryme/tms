package com.websopti.tms.workingTask.rest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Rahul Padaliya on 9/13/2016.
 */
public interface RunningTaskApiInterface {

    //http://192.168.1.11:8090/api/allForAdmin/{status}/{key}/{pageNum}?groupBy=Priority
    //@FormUrlEncoded
    @GET("/allForAdmin/{status}/{key}/{pageNum}")
    void getRunningTaskList(
           @Path("status") String status,
           @Path("key") String key,
           @Path("pageNum") String pageNum,
           @Query("groupBy") String groupBy,
           Callback<Response> response);
}
