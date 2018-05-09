package com.websopti.tms.workingTask.rest;

import com.websopti.tms.workingTask.model.AvailableResourceModel;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Rahul Padaliya on 9/13/2016.
 */
public interface AvailableResourceApiInterface {

    //http://192.168.1.11:8090/api/resource/available/{key}
    //@FormUrlEncoded
    @GET("/resource/available/{key}")
    void getAvailableResource(
            @Path("key") String key,
            Callback<AvailableResourceModel> response);
}
