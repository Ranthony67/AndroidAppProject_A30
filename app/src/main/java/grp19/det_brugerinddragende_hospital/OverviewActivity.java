package grp19.det_brugerinddragende_hospital;

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

import grp19.det_brugerinddragende_hospital.Api.ChildrenService;
import grp19.det_brugerinddragende_hospital.Api.LoginService;
import grp19.det_brugerinddragende_hospital.Api.ServiceGenerator;
import grp19.det_brugerinddragende_hospital.Helpers.ChildCardAdapter;
import grp19.det_brugerinddragende_hospital.Models.Child;
import grp19.det_brugerinddragende_hospital.Services.ResubmitService;
import retrofit2.Call;

public class OverviewActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ChildCardAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView childRecyclerView;
    private static ArrayList<Child> childrenData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.childSwipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("swipeContainer", "Refreshing");
                new FetchChildrenTask().execute();
            }
        });

        childRecyclerView = (RecyclerView) findViewById(R.id.childRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        childRecyclerView.setLayoutManager(layoutManager);

        try {
            childrenData = MainApplication.getUserInfo().children;
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ChildCardAdapter(childrenData, this);
        childRecyclerView.setAdapter(adapter);

        Intent intent = new Intent(this, ResubmitService.class);
        startService(intent);
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

            } catch (NullPointerException ex) {
                ex.printStackTrace();
                return false;
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
