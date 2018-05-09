package com.websopti.tms.login.rest;


import com.websopti.tms.login.model.LoginResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Hiren Kapuria on 7/14/2016.
 */
public interface LoginApiInterface {

    @FormUrlEncoded
    @POST("/auth")
    void getLogin(
            @Field("email") String username,
            @Field("password") String password,
            Callback<LoginResponse> response);

}
