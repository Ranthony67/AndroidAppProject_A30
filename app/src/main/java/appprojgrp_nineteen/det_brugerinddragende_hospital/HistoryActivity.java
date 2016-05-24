package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

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

    private Child _child = new Child();
    private DatabaseHelper dbHelper;
    private SwipeRefreshLayout swipeContainer;
    private ReportListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView reportRecyclerView;
    private static ArrayList<Report> _reportData = new ArrayList<>();

    HistoryActivity(Child child){ _child = child; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.reportSwipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchReportsTask().execute();
            }
        });


    }

    private class FetchReportsTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            ReportService reportService = ServiceGenerator.createService(ReportService.class);
            Call<List<Report>> call = reportService.getAllReports(_child.id);

            try {
                List<Report> reports = call.execute().body();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
        }
    }
}