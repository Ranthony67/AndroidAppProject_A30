package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers.ChildCardAdapter;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;

public class OverviewActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Child> childrenData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        dbHelper = DatabaseHelper.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        /*Child child = new Child();
        child.name = "Alexander Kledal";
        child.department = "Semi-Intensiv";
        child.save();*/


        try {
            childrenData = dbHelper.getAll(Child.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ChildCardAdapter(childrenData);
        recyclerView.setAdapter(adapter);
    }
}
