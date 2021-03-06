package grp19.det_brugerinddragende_hospital.Services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import grp19.det_brugerinddragende_hospital.Api.ReportService;
import grp19.det_brugerinddragende_hospital.Api.ServiceGenerator;
import grp19.det_brugerinddragende_hospital.Database.DatabaseHelper;
import grp19.det_brugerinddragende_hospital.MainApplication;
import grp19.det_brugerinddragende_hospital.Models.Report;
import retrofit2.Call;

public class SyncService extends IntentService {

    public static final String PARAMS_REPORT_ID = "report-id";

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int reportId = intent.getIntExtra(PARAMS_REPORT_ID, 0);
        if (reportId == 0) return;

        if (!MainApplication.networkAvailable()) {
            Log.v("SyncService", "No internet / not connected.");
            return;
        }

        try {
            Report report = DatabaseHelper.getInstance().find(Report.class, reportId);
            ReportService reportService = ServiceGenerator.createService(ReportService.class);

            Call<Report> call = reportService.create(report);
            Report apiReport = call.execute().body();

            report.remoteId = apiReport.id;
            report.syncDone();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("SyncService", "HandleIntent");
    }
}
