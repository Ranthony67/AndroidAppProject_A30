package appprojgrp_nineteen.det_brugerinddragende_hospital.Services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Constants.Constants;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Receivers.ResubmitReceiver;

public class ResubmitService extends IntentService {
    public ResubmitService() {
        super("ResubmitService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("ResubmitService", "onStartCommand");
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        rescheduleResubmit();
        setupBroadcastReceivers();
    }

    public void rescheduleResubmit() {
        Log.v("ResubmitService", "rescheduleResubmit");

        Intent alarmIntent = new Intent(this, ResubmitReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        long firstGoOff = System.currentTimeMillis() + Constants.FETCH_REPEAT;
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstGoOff, pending);
    }

    public void setupBroadcastReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BACKGROUND_RESUBMIT_TRIGGER);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("broadcastReceiver", "onReceive");
            String action = intent.getAction();
            switch (action) {
                case Constants.BACKGROUND_RESUBMIT_TRIGGER:
                    resubmitReports();
                    break;
            }
        }
    };

    private void resubmitReports() {
        try {
            List<Report> reports = DatabaseHelper.getInstance().getAll(Report.class, "synced_with_remote = 0");

            for(int i = 0; i < reports.size(); i++) {
                Report report = reports.get(i);
                Intent syncIntent = new Intent(ResubmitService.this, SyncService.class);
                syncIntent.putExtra(SyncService.PARAMS_REPORT_ID, report.id);
                startService(syncIntent);
            }

            rescheduleResubmit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
