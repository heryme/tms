package com.websopti.tms.utility;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DELL on 25-11-2015.
 */
public class UserSession {

    private Context mContext;
    private String accessToken;
    private Long expiresIn;
    private String tokenType;
    private String scope;
    private String refreshToken;
    private String userName;
    private String password;
    public static final String PREFERENCE_NAME = "TMSPref";
    public static final String USER_ID = "user_id";
    public static final String SPINNER_ITEM = "spinner_item";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static UserSession userSession;

    /*public static UserSession getInstance(Context context) {
        if (userSession == null)
            userSession = new UserSession(context);

        return userSession;

    }*/

    public UserSession(Context context) {
        this.mContext = context;
        sharedPreferences = this.mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * Set User Id
     * @param userId
     */
    public void setUserId(String userId) {
        editor.putString(USER_ID,userId);
        editor.commit();
    }

    /**
     * Get User Id
     * @return
     */
    public String getUserId() {
        return getSharedPreferences().getString(USER_ID,"");
    }

    /**
     * Set Spinner Item
     * @param item
     */
    public void setGroupBy(String item) {
        editor.putString(SPINNER_ITEM,item);
        editor.commit();
    }

    /**
     * Get Spinner Item
     * @return
     */
    public String getSpinnerItem() {
        return getSharedPreferences().getString(SPINNER_ITEM,"");
    }



   /* public static void buildUserSession(Context context, JSONObject responseObj) throws JSONException {
        *//*UserSession userSession = UserSession.getInstance(context);
        userSession.setAccessToken(responseObj.getString("access_token"));
        userSession.setExpiresIn(responseObj.getLong("expires_in"));
        if (responseObj.has("refresh_token"))
            userSession.setRefreshToken(responseObj.getString("refresh_token"));

        userSession.setScope(responseObj.getString("scope"));
        userSession.setTokenType(responseObj.getString("token_type"));
        return userSession;*//*

        userSession.setAccessTokenInPref(responseObj.getString(userSession.KEY_ACCESS_TOKEN));
        userSession.setTokenTypeInPref(responseObj.getString(userSession.KEY_TOKEN_TYPE));
        userSession.setTokenExpireInPref(responseObj.getString(userSession.KEY_EXPIRE_IN));
        userSession.setScopeInPref(responseObj.getString(userSession.KEY_SCOPE));

        if (responseObj.has(userSession.KEY_REFRESH_TOKEN))
            userSession.setRefreshTokenInPref(responseObj.getString(userSession.KEY_REFRESH_TOKEN));

    }

    public void clearUserSession(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.remove(KEY_REFRESH_TOKEN);
        editor.remove(KEY_SCOPE);
        editor.remove(KEY_EXPIRE_IN);
        editor.remove(KEY_TOKEN_TYPE);
        editor.remove(KEY_IS_DEVICE_REGISTER);
        editor.commit();
    }

    public void setAccessTokenInPref(String token) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.commit();
    }

    public void setTokenTypeInPref(String tokenType) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_TOKEN_TYPE, tokenType);
        editor.commit();
    }

    public void setTokenExpireInPref(String tokenExpireIn) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_EXPIRE_IN, tokenExpireIn);
        editor.commit();
    }

    public void setScopeInPref(String scope) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_SCOPE, scope);
        editor.commit();
    }

    public void setRefreshTokenInPref(String refreshToken) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.commit();
    }*/
}
