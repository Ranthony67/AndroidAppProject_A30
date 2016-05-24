package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.LoginService;

/**
 * Snatched from:
 * http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
 */
public class MainApplication extends Application {
    private static Context context;
    private static String authToken;
    private static LoginService.UserInfo userInfo;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        MainApplication.authToken = authToken;
    }

    public static LoginService.UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(LoginService.UserInfo userInfo) {
        MainApplication.userInfo = userInfo;
    }

    public void onCreate() {
        super.onCreate();
        Log.v("MainApplication", "onCreate");
        setAuthToken("");
        MainApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }
}
