package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.LoginService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ServiceGenerator;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Constants.Constants;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    Button btnSave;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btnSave = (Button) findViewById(R.id.btnSave);
        mDatabaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Report report = new Report();

                report.remoteId = 1;
                report.name = "Testing";
                report.syncedWithRemote = true;

                Log.v("ReportActivity", "Class: " + report.tableName());

                mDatabaseHelper.insertModel(report);
                */
            }
        });
    }
}
