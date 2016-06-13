package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ReportService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ServiceGenerator;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers.ReportListAdapter;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;
import retrofit2.Call;

public class HistoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SwipeRefreshLayout swipeContainer;
    private ReportListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView reportRecyclerView;
    private static ArrayList<Report> _reportData = new ArrayList<>();
    private int child_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent passedIntent = getIntent();
        if (passedIntent != null) {
            int childId = passedIntent.getIntExtra("child-id", -1);
            String childName = passedIntent.getStringExtra("child-name");

            child_id = childId;
            setTitle(childName);

            Log.v("ReportActivity", "Intent, child-id: " + passedIntent.getIntExtra("child-id", 0));
        }

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.reportSwipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchReportsTask().execute();
            }
        });

        reportRecyclerView = (RecyclerView) findViewById(R.id.reportRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        reportRecyclerView.setLayoutManager(layoutManager);

        adapter = new ReportListAdapter(_reportData, this);
        reportRecyclerView.setAdapter(adapter);

        new FetchReportsTask().execute();
    }

    private class FetchReportsTask extends AsyncTask<Void, Void, Boolean>{

        private List<Report> reports;

        @Override
        protected Boolean doInBackground(Void... voids) {

            ReportService reportService = ServiceGenerator.createService(ReportService.class);
            Call<List<Report>> call = reportService.getAllReports(child_id);

            try {
                reports = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            UpdateReports(reports);

            swipeContainer.setRefreshing(false);
        }
    }

    public void UpdateReports(List<Report> reports){
        adapter.clear();
        adapter.addAll(reports);
    }
}
