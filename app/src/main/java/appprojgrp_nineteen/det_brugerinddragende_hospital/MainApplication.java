package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.app.Application;
import android.content.Context;

/**
 * Snatched from:
 * http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
 */
public class MainApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MainApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MainApplication.context;
    }
}
