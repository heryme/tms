package com.websopti.tms.rest;


import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Hiren Kapuria on 7/14/2016.
 */
public class ApiClient {

    public static final String BASE_URL = "http://192.168.1.20:8080/api/";
    //public static final String BASE_URL = "http://wo.noip.me:92/api/";//Live
    //public static final String BASE_URL = "http://192.168.1.51:92/api/";//Live
     // public static final String BASE_URL = "http://192.168.1.51/wotms_test/api/"; //Live For Testing

    //Rest Adapter
    private static RestAdapter adapter;

    public static RestAdapter getRestAdapter() {
        if (adapter == null) {
            adapter = new RestAdapter.Builder().setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                 /*if(sessionManager != null) {
                        request.addHeader("Authorization", "Bearer "+sessionManager.getAccessToken());
                   }*/
                }
            })
            .setEndpoint(BASE_URL) //Setting the Root URL
            .build(); //Finally building the adapter
        }
        return adapter;
    }
}
