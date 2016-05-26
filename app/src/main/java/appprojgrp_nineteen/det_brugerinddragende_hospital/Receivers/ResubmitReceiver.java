package appprojgrp_nineteen.det_brugerinddragende_hospital.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Constants.Constants;

public class ResubmitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("ResubmitReceiver", "onReceive");
        Intent resubmitSync = new Intent();
        resubmitSync.setAction(Constants.BACKGROUND_RESUBMIT_TRIGGER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(resubmitSync);
    }
}
