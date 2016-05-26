package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.LoginService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ServiceGenerator;
import retrofit2.Call;

/**
 * Snatched from:
 * http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
 */
public class MainApplication extends Application {
    private static final String APP_IDENTIFIER = "com.brugerinddraget";
    private static final String SHARED_PREF_AUTH_TOKEN_KEY = "auth_token";


    private static Context context;
    private static String authToken;
    private static LoginService.UserInfo userInfo;
    private static SharedPreferences preferences;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        preferences.edit().putString(SHARED_PREF_AUTH_TOKEN_KEY, authToken).commit();
        MainApplication.authToken = authToken;
    }

    public static void signOut() {
        preferences.edit().putString(SHARED_PREF_AUTH_TOKEN_KEY, "").commit();
        MainApplication.authToken = "";
        new SignOutTask().execute();
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
        MainApplication.context = getApplicationContext();
        preferences = getAppContext().getSharedPreferences(APP_IDENTIFIER, MODE_PRIVATE);
        loadAuthToken();
    }

    private void loadAuthToken() {
        authToken = preferences.getString(SHARED_PREF_AUTH_TOKEN_KEY, "");
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }

    public static boolean isSignedIn() {
        return getAuthToken() != "";
    }

    public static boolean networkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static class SignOutTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                LoginService client = ServiceGenerator.createService(LoginService.class);

                Call<Boolean> call = client.signout();
                call.execute().body();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
