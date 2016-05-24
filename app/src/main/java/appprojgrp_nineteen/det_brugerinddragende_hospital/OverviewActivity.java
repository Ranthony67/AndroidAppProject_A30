package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ChildrenService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.LoginService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.ServiceGenerator;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers.ChildCardAdapter;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import retrofit2.Call;

public class OverviewActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ChildCardAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Child> childrenData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("swipeContainer", "Refreshing");
                new FetchChildrenTask().execute();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            childrenData = MainApplication.getUserInfo().children;
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ChildCardAdapter(childrenData, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.options_sign_out:
                handleApiErrorAndSignOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("OverviewActivity", "onActivityResult requestCode: " + requestCode + ", resultCode: ");
    }

    public class FetchChildrenTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ChildrenService client = ServiceGenerator.createService(ChildrenService.class);
            Call<List<Child>> call = client.getAllChildren();
            try {
                List<Child> children = call.execute().body();
                LoginService.UserInfo info = MainApplication.getUserInfo();
                info.children = (ArrayList<Child>) children;

                MainApplication.setUserInfo(info);
                Log.v("swipeContainer", "Children count: " + children.size());

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                adapter.clear();
                adapter.addAll(MainApplication.getUserInfo().children);
            } else {
                handleApiErrorAndSignOut();
            }

            swipeContainer.setRefreshing(false);
        }
    }

    private void handleApiErrorAndSignOut() {
        MainApplication.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
