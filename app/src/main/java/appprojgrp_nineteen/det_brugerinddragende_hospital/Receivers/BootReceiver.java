package appprojgrp_nineteen.det_brugerinddragende_hospital.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Services.ResubmitService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;

        Log.v("BootReceiver", "onBoot");

        Intent serviceIntent = new Intent(context, ResubmitService.class);
        context.startService(serviceIntent);
    }
}
