package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;

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

                Report report = new Report();

                report.remoteId = 1;
                report.name = "Testing";
                report.syncedWithRemote = true;

                Log.v("ReportActivity", "Class: " + report.tableName());

                mDatabaseHelper.insertModel(report);
            }
        });
    }
}
